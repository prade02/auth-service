package com.auth.security.filter;

import com.auth.models.AuthRequest;
import com.auth.security.jwt.JwtConfig;
import com.auth.security.jwt.JwtTokenGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenGenerator jwtTokenGenerator;
  private final JwtConfig jwtConfig;

  @Override
  @SneakyThrows
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    AuthRequest authRequest =
        new ObjectMapper().readValue(request.getInputStream(), AuthRequest.class);
    Authentication authenticationRequest =
        new UsernamePasswordAuthenticationToken(
            authRequest.getUsername(), authRequest.getPassword());
    return authenticationManager.authenticate(authenticationRequest);
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult) {
    response.addHeader(
        jwtConfig.authorizationHeader(),
        jwtConfig.getTokenPrefix() + " " + jwtTokenGenerator.generateToken(authResult));
  }
}
