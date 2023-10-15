package com.mqtt.admin.config;

import com.mqtt.admin.config.authentication.JwtAuthenticationProvider;
import com.mqtt.admin.config.authentication.JwtAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthManager implements AuthenticationManager {
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    public AuthManager(JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (JwtAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return jwtAuthenticationProvider.authenticate(authentication);
        }
        return null;
    }
}
