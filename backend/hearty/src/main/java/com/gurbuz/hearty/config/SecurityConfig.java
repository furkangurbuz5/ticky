package com.gurbuz.hearty.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import static org.springframework.security.config.Customizer.*;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import com.gurbuz.hearty.util.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final JwtRequestFilter jwtRequestFilter;
  private final RsaKeyProperties rsaKeys;

  public SecurityConfig(JwtRequestFilter filter, RsaKeyProperties rsaKeys) {
    this.jwtRequestFilter = filter;
    this.rsaKeys = rsaKeys;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**")
        .permitAll()
        .requestMatchers("/api/tickets")
        .authenticated()
        .requestMatchers("/h2-console/**")
        .permitAll()
        .anyRequest()
        .authenticated())
      .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .httpBasic(withDefaults())
      .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
//      .addFilterBefore(this.jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(this.rsaKeys.publicKey()).build();
  }

  @Bean
  JwtEncoder jwtEncoder(){
    JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwks);
  }

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user = User.builder()
      .username("user@example.com")
      .password(this.encoder().encode("password"))
      .roles("USER")
      .build();

    UserDetails admin = User.builder()
      .username("admin@example.com")
      .password(this.encoder().encode("password"))
      .roles("ADMIN")
      .build();

    return new InMemoryUserDetailsManager(user, admin);
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authManager(AuthenticationConfiguration authConfiguration) throws Exception {
    return authConfiguration.getAuthenticationManager();
  }

}
