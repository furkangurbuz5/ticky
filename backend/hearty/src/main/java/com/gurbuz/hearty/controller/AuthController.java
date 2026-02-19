package com.gurbuz.hearty.controller;

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
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public AuthController(
            AuthenticationManager authManager,
            JwtUtil jwtUtil,
            UserDetailsService userDetailsService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));

        final UserDetails userDetails = (UserDetails) auth.getPrincipal();
        final String jwt = jwtUtil.generateToken(userDetails);

        if (auth.isAuthenticated()) {
            return ResponseEntity.ok("Bearer " + jwt);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

    }
}
