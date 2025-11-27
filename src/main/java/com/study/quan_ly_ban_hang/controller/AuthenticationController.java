package com.study.quan_ly_ban_hang.controller;

import com.nimbusds.jose.JOSEException;
import com.study.quan_ly_ban_hang.dto.request.*;
import com.study.quan_ly_ban_hang.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ApiResponse.builder()
                .data(authenticationService.authenticate(authenticationRequest))
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse refresh(@RequestBody RefreshTokenRequest request) throws ParseException, JOSEException {
        return ApiResponse.builder()
                .data(authenticationService.refreshToken(request))
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        return ApiResponse.builder().data(authenticationService.introspect(request)).build();
    }

    @PostMapping("/logout")
    public ApiResponse logout(@RequestBody InvalidTokenRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.builder().build();
    }

}
