package com.mqtt.admin.config;

import com.mqtt.admin.config.authentication.JwtAuthenticationToken;
import com.mqtt.admin.utils.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class MqttJwtAuthenticationFilter extends OncePerRequestFilter {
    private final static Logger logger = Logger.getLogger("MqttAuthenticationFilter");
    private AuthManager authManager;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    public MqttJwtAuthenticationFilter(AuthManager authManager, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("This filter is being triggered!");
        String token = request.getHeader("Authorization");

        this.authManager.authenticate(new JwtAuthenticationToken(null, token));
        String username = Jwt.getClaim("username", token);
        MqttUserDetails userDetails = (MqttUserDetails) userDetailsService.loadUserByUsername(username);

        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(userDetails, null, userDetails.getAuthorities()));

        filterChain.doFilter(request, response);
    }
}
