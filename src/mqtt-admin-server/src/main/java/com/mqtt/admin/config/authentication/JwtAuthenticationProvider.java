package com.mqtt.admin.config.authentication;

import com.mqtt.admin.utils.Jwt;
import io.jsonwebtoken.Claims;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private static Logger logger = Logger.getLogger("JwtAuthenticationProvider");
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.info(authentication.getCredentials().toString());
        if (!Jwt.isAuthenticated((String) authentication.getCredentials())) {
            throw new BadCredentialsException("Invalid token");
        }
        return new JwtAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), authentication.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
