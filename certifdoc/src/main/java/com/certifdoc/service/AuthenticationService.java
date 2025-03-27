package com.certifdoc.service;

import com.certifdoc.dto.SignUpRequest;
import com.certifdoc.entity.User;

public class AuthenticationService {
    User signup(SignUpRequest signUpRequest);
	JwtAuthenticationResponse signIn(SignInRequest signInRequest);
	JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
