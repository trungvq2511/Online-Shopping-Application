package com.example.onlineshopping.onlineshoppingsystem.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.onlineshopping.onlineshoppingsystem.security.WebSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/login") || request.getServletPath().equals("/token/refresh/**")) {
            // let filter pass the request, go through
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                //if token is valid
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    //decode
                    Algorithm algorithm = Algorithm.HMAC256(WebSecurityConfig.SECRET_KEY);
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    //authenticate
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    for (String role : roles) {
                        authorities.add(new SimpleGrantedAuthority(role));
                    }
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    // let filter pass the request, go through
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    //if token is invalid
                    Map<String, String> errors = new HashMap<>();
                    errors.put("error", e.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    new ObjectMapper().writeValue(response.getOutputStream(), errors);
                }
            } else {
                // let filter pass the request, go through
                filterChain.doFilter(request, response);
            }
        }
    }
}
