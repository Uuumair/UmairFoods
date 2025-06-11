package com.umair.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class JwtProvider {

    // Secret key for signing JWT tokens
    private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    // Generate JWT token for authenticated user
    public String generateToken(Authentication authentication) {
        // Get user authorities (roles)
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles = populateAuthorities(authorities);

        // Build the JWT token with claims (email and authorities)
        String jwt = Jwts.builder()
                .setIssuedAt(new Date())  // Set token issued time
                .setExpiration(new Date(new Date().getTime() + 86400000))  // Set token expiration time (1 day)
                .claim("email", authentication.getName())  // Store email as claim
                .claim("authorities", roles)  // Store authorities as claim
                .signWith(key)  // Sign the token with the secret key
                .compact();

        return jwt;
    }

    // Extract email from JWT token
    public String getEmailFromJwtToken(String jwt) {
        // Remove "Bearer " prefix if present
        jwt = jwt.substring(7);
        // Parse the JWT token to get claims
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        // Return the email from the claims
        return String.valueOf(claims.get("email"));
    }

    // Convert collection of authorities (roles) into a comma-separated string
    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            // Prefix each role with "ROLE_" to follow Spring Security convention
            auths.add("ROLE_" + authority.getAuthority());
        }
        return String.join(",", auths);
    }
}
