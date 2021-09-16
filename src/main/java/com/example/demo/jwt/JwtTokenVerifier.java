package com.example.demo.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {

    private final  JwtConfig jwtConfig;

    public JwtTokenVerifier(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //get the token from the header
        String authorizationHeader=request.getHeader(jwtConfig.getAuthprizationHeader());
        if(Strings.isNullOrEmpty(authorizationHeader) || !(authorizationHeader.startsWith(jwtConfig.getPrefix())))
        {
            //reject the request
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String token=authorizationHeader.replace(jwtConfig.getPrefix(), "");
            Jwt<JwsHeader, Claims> claimsJwt=Jwts.parser()
                    .setSigningKey(
                            jwtConfig.getTheSecretKey())
                    .parseClaimsJws(token);
            Claims body=(Claims) claimsJwt.getBody();
            String username=body.getSubject();
            List<Map<String, String>> authorities=(List)body.get("authorities");
            //from this point, everything is fine and user can be authenticated
            Set<SimpleGrantedAuthority> simpleGrantedAuthorities=authorities.stream().map(
                    m->new SimpleGrantedAuthority(m.get("authority"))
            ).collect(Collectors.toSet());
            Authentication authentication=new UsernamePasswordAuthenticationToken(
                    username, null, simpleGrantedAuthorities

            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        catch (JwtException e){
            throw new IllegalStateException("Token can not be trusted.");
        }
        filterChain.doFilter(request, response);
    }
}
