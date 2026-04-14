package com.codej.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            log.info("Incoming request: method={} uri={} remoteAddr={}", request.getMethod(), request.getRequestURI(), request.getRemoteAddr());

            // log common headers
            String auth = request.getHeader("Authorization");
            log.info("Authorization header present={}", auth != null);
            if (auth != null) {
                log.debug("Authorization header value={}", auth);
            }

            String origin = request.getHeader("Origin");
            if (origin != null) log.debug("Origin={}", origin);

            // log all headers at debug level
            if (log.isDebugEnabled()) {
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String name = headerNames.nextElement();
                        log.debug("Header: {}={}", name, Collections.list(request.getHeaders(name)));
                    }
                }
            }
        } catch (Exception e) {
            log.warn("RequestLoggingFilter failed: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
