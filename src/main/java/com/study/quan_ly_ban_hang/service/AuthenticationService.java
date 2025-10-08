package com.study.quan_ly_ban_hang.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimNames;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.study.quan_ly_ban_hang.dto.request.AuthenticationRequest;
import com.study.quan_ly_ban_hang.dto.request.IntrospectRequest;
import com.study.quan_ly_ban_hang.dto.response.AuthenticationResponse;
import com.study.quan_ly_ban_hang.dto.response.IntrospectResponse;
import com.study.quan_ly_ban_hang.entity.User;
import com.study.quan_ly_ban_hang.exception.AppException;
import com.study.quan_ly_ban_hang.exception.ErrorCode;
import com.study.quan_ly_ban_hang.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;

    @NonFinal
    protected static final String SIGNER_KEY = "3fa2b6e78c14f0a19d45d9f6c73e2a58a7b6c12d4f83a9e0e7c93f5b8c6d10a4";

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean isMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isMatch) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String token = generateToken(request.getUsername());
        return AuthenticationResponse.builder().authenticated(isMatch).token(token).build();
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(request.getToken());

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean valid = signedJWT.verify(verifier) && expiryTime.after(new Date());

        return IntrospectResponse.builder()
                .valid(valid)
                .build();
    }

    private String generateToken(String username) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("PhuSang.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("customClaim", "custom")
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
}
