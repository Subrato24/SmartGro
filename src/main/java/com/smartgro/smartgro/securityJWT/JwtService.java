//package com.smartgro.smartgro.securityJWT;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Service;
//import org.springframework.beans.factory.annotation.Value;
//import java.nio.charset.StandardCharsets;
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.Date;
//
//@Service
//public class JwtService {
//    @Value("${app.jwt.secret}")
//    private String secret;
//    @Value("${app.jwt.expiration-minutes}")
//    private long expiryMins;
//
//    public String generateToken(String subject) {
//        Instant now = Instant.now();
//        return Jwts.builder()
//                .setSubject(subject)
//                .setIssuedAt(Date.from(now))
//                .setExpiration(Date.from(now.plus(expiryMins, ChronoUnit.MINUTES)))
//                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
//                .compact();
//    }
//}
