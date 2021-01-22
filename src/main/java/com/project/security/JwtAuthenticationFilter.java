package com.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private SecurityContextAccessor contextAccessor;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(!isEmpty(token) && jwtService.isTokenValid(token)) {
            final String username = jwtService.getUserEmailFromToken(token);
            final Integer userId = jwtService.getUserIdFromToken(token);
            if(username != null && userId != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
                authentication.setDetails(new UserDetails(userId, username));
                contextAccessor.setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
