package com.example.authentication.utils;

import com.example.authentication.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long JWT_TOKEN_VALIDITY;

    private SecretKey decodeSecretKey() {
        log.info("Original secret: " + SECRET_KEY);
        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
        log.info("Decoded secret: " + Arrays.toString(decodedKey));
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 100))
                .signWith(decodeSecretKey())
                .compact();
    }
}
