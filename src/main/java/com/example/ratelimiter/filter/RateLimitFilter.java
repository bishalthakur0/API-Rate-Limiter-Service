package com.example.ratelimiter.filter;

import com.example.ratelimiter.service.RateLimiterService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class RateLimitFilter implements Filter {

    @Autowired
    private RateLimiterService rateLimiterService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Exclude Usage API from rate limiting (optional, but good practice for
        // dashboard)
        if (req.getRequestURI().startsWith("/api/usage")) {
            chain.doFilter(request, response);
            return;
        }

        String userId = req.getHeader("X-USER-ID");
        if (userId == null || userId.isEmpty()) {
            userId = req.getRemoteAddr(); // Fallback to IP
        }

        if (rateLimiterService.checkRequest(userId)) {
            // Allowed
            // Add headers (mocking values for now, can be retrieved from service if needed)
            res.addHeader("X-RateLimit-Limit", "10");
            chain.doFilter(request, response);
        } else {
            // Blocked
            res.setStatus(429); // Too Many Requests
            res.getWriter().write("Too Many Requests");
            res.addHeader("X-RateLimit-Limit", "10");
            // res.addHeader("Retry-After", "...");
        }
    }
}
