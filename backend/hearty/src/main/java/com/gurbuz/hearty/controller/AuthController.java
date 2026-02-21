package com.gurbuz.hearty.controller;

import com.gurbuz.hearty.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gurbuz.hearty.model.AuthRequest;
import com.gurbuz.hearty.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthenticationManager authManager;
//  private final JwtUtil jwtUtil;
  private final TokenService tokenService;
  private final UserDetailsService userDetailsService;

  private static final Logger log = LoggerFactory.getLogger(AuthController.class);

  public AuthController(
    AuthenticationManager authManager,
    JwtUtil jwtUtil,
    TokenService tokenService,
    UserDetailsService userDetailsService) {
    this.authManager = authManager;
//    this.jwtUtil = jwtUtil;
    this.tokenService = tokenService;
    this.userDetailsService = userDetailsService;
  }

//  @PostMapping("/login")
//  public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
//
//    Authentication auth = authManager.authenticate(
//      new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
//
//    final UserDetails userDetails = this.userDetailsService.loadUserByUsername(authRequest.username());
//    final String jwt = jwtUtil.generateToken(userDetails);
//
//    System.out.println();
//
//    if (auth.isAuthenticated()) {
//      return ResponseEntity.ok(jwt);
//    } else {
//      return ResponseEntity.status(401).body("Invalid credentials");
//    }
//  }

  @PostMapping("/token")
  public String token(Authentication auth){
    log.debug("Authentication name: {}", auth.getName());
    return tokenService.generateToken(auth);
  }
}
