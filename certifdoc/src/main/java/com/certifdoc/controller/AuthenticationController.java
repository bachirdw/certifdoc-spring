package com.certifdoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.certifdoc.dto.SignInRequest;
import com.certifdoc.dto.SignUpRequest;
import com.certifdoc.entity.User;
import com.certifdoc.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> signin(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationService.signin(signInRequest));
    }
}