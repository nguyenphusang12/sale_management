package com.study.quan_ly_ban_hang.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.study.quan_ly_ban_hang.dto.request.AuthenticationRequest;
import com.study.quan_ly_ban_hang.dto.request.IntrospectRequest;
import com.study.quan_ly_ban_hang.dto.request.InvalidTokenRequest;
import com.study.quan_ly_ban_hang.dto.request.RefreshTokenRequest;
import com.study.quan_ly_ban_hang.dto.response.AuthenticationResponse;
import com.study.quan_ly_ban_hang.dto.response.IntrospectResponse;
import com.study.quan_ly_ban_hang.entity.InvalidToken;
import com.study.quan_ly_ban_hang.entity.User;
import com.study.quan_ly_ban_hang.exception.AppException;
import com.study.quan_ly_ban_hang.exception.ErrorCode;
import com.study.quan_ly_ban_hang.repository.InvalidTokenRepository;
import com.study.quan_ly_ban_hang.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    InvalidTokenRepository invalidTokenRepository;
    PasswordEncoder passwordEncoder;


    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.token-duration}")
    protected Long TOKEN_DURATION;

    @NonFinal
    @Value("${jwt.refresh-token-duration}")
    protected Long REFRESH_TOKEN_DURATION;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        boolean isMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isMatch) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String token = generateToken(user);
        return AuthenticationResponse.builder().authenticated(isMatch).token(token).build();
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        boolean isValid = true;
        try {
            verifyToken(request.getToken(), false);
        } catch (AppException e) {
            isValid = false;
        }
        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException {
        SignedJWT jwt = verifyToken(request.getToken(), true);
        String id = jwt.getJWTClaimsSet().getJWTID();
        Date expiryDate = jwt.getJWTClaimsSet().getExpirationTime();
        User user = userRepository.findByUsername(jwt.getJWTClaimsSet().getSubject()).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        InvalidToken invalidToken = InvalidToken.builder()
                .id(id)
                .expiryDate(expiryDate)
                .build();
        invalidTokenRepository.save(invalidToken);

        return AuthenticationResponse.builder().authenticated(true).token(generateToken(user)).build();
    }

    public void logout(InvalidTokenRequest request) throws ParseException, JOSEException {
        try {
            SignedJWT jwt = verifyToken(request.getToken(), true);
            String id = jwt.getJWTClaimsSet().getJWTID();
            Date expiryDate = jwt.getJWTClaimsSet().getExpirationTime();

            InvalidToken invalidToken = InvalidToken.builder()
                    .id(id)
                    .expiryDate(expiryDate)
                    .build();
            invalidTokenRepository.save(invalidToken);
        } catch (AppException e) {
            System.out.println(e.getMessage());
        }
    }

    public SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh) ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESH_TOKEN_DURATION, ChronoUnit.SECONDS).toEpochMilli()) :
                signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean valid = signedJWT.verify(verifier) && expiryTime.after(new Date());

        if (!valid) throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (invalidTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }

    private String generateToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("PhuSang.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(TOKEN_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> {
                        stringJoiner.add(permission.getName());
                    });
                }
            });
        }
        return stringJoiner.toString();
    }
}
