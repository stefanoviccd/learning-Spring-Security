package com.example.demo.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtUUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final AuthenticationManager authenticationManager;

    public JwtUUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
       try {
           //read values from request and put them in UserAndPassrordRequest class
           UsernameAndPasswordAuthenticationRequest authenticationRequest=new ObjectMapper().readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);
            Authentication authentication=new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );
            //return type is Authentication
            return authenticationManager.authenticate(authentication);

       }
       catch (IOException e){
           throw new RuntimeException(e);
       }
        }
}
