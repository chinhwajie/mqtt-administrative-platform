package com.mqtt.admin.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public final AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        // Extract roles and permissions from the resource_access claim
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess != null) {
            for (Map.Entry<String, Object> entry : resourceAccess.entrySet()) {
                if (entry.getValue() instanceof Map) {
                    Map<String, Object> resourceRoles = (Map<String, Object>) entry.getValue();
                    for (Map.Entry<String, Object> roleEntry : resourceRoles.entrySet()) {
                        if ("roles".equals(roleEntry.getKey())) {
                            List<String> roles = (List<String>) roleEntry.getValue();
                            for (String role : roles) {
                                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                            }
                        }
                        // Add additional logic to extract and map permissions if needed
                    }
                }
            }
        }

        return new JwtAuthenticationToken(jwt, authorities);
    }
}
