package com.codej.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String jwt = null;
        String username = null;

        log.debug("JwtAuthenticationFilter - requestURI={} method={} authHeaderPresent={}", request.getRequestURI(), request.getMethod(), authHeader != null);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            log.debug("JwtAuthenticationFilter - token extracted length={}", jwt.length());
            try {
                if (jwtUtil.validateToken(jwt)) {
                    Claims claims = jwtUtil.getClaims(jwt);
                    username = claims.getSubject();
                    log.debug("JwtAuthenticationFilter - token valid, subject={}", username);
                } else {
                    log.warn("JwtAuthenticationFilter - token invalid for requestURI={}", request.getRequestURI());
                }
            } catch (Exception e) {
                log.warn("JwtAuthenticationFilter - error validating token: {}", e.getMessage());
            }
        } else {
            log.debug("JwtAuthenticationFilter - no bearer token found in Authorization header");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                log.debug("JwtAuthenticationFilter - userDetails loaded for username={}", username);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.debug("JwtAuthenticationFilter - authentication set for username={}", username);
            } catch (Exception e) {
                log.warn("JwtAuthenticationFilter - failed to load user by username {}: {}", username, e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
