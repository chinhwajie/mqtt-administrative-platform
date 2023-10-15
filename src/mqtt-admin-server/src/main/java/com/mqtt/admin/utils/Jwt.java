package com.mqtt.admin.utils;

import com.mqtt.admin.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;

import java.security.PublicKey;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class Jwt {
    @Value("${signing.key}")
    private static String signingKey;

    private final static Logger logger = Logger.getLogger("Jwt");

    public static boolean isAuthenticated(String token) {
        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token);
            Claims claims = jws.getBody();
            String username = claims.getSubject();
            logger.info("Username: " + username);
            return true;
        } catch (SignatureException e) {
            // Handle signature verification failure
            e.printStackTrace();
        } catch (Exception e) {
            // Handle general exceptions
            e.printStackTrace();
        }

        // Token is invalid
        return false;
    }

    public static Claims getClaims(String token) {
        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token);
            Claims claims = jws.getBody();
            return claims;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getClaim(String claim, String token) {
        return Objects.requireNonNull(getClaims(token)).get(claim, String.class);
    }

    public static String generateJwt(User user) {
        // Set the JWT issuer, subject, and expiration
        String issuer = "your-issuer";
        long expirationTime = System.currentTimeMillis() + 3600000; // 1 hour

        // Build the JWT
        String jwt = Jwts.builder()
                .setIssuer(issuer)
                .setSubject(user.getId().toString())
                .setExpiration(new Date(expirationTime))
                .addClaims(Map.of(
                        user.getUsername(),
                        user.getEmail()
                ))
                .signWith(SignatureAlgorithm.HS256, signingKey) // Use HS256 or other algorithm
                .compact();

        return jwt;
    }
}
