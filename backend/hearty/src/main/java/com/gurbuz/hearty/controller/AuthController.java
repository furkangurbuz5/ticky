package com.gurbuz.hearty.controller;

import com.gurbuz.hearty.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthenticationManager authManager;
  private final TokenService tokenService;
  private final UserDetailsService userDetailsService;

  private static final Logger log = LoggerFactory.getLogger(AuthController.class);

  public AuthController(
    AuthenticationManager authManager,
    TokenService tokenService,
    UserDetailsService userDetailsService) {
    this.authManager = authManager;
//    this.jwtUtil = jwtUtil;
    this.tokenService = tokenService;
    this.userDetailsService = userDetailsService;
  }

  @PostMapping("/token")
  public String token(Authentication auth){
    log.debug("Authentication name: {}", auth.getName());
    return tokenService.generateToken(auth);
  }
}
