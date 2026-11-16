package io.github.matts.mercure.auth;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTFactory {

    public static JwtBuilder createBuilder(MercureClaim mercureClaim, long expiration, String secret) {
        Map<String, Object> jwtClaims = new HashMap<>();
        jwtClaims.put("mercure", mercureClaim);

        // Mercure hub keys are raw strings (not Base64); jjwt 0.12+ requires >= 32 bytes for HS256.
        return Jwts.builder()
                .header().add("typ", "JWT").and()
                .claims(jwtClaims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(expiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), Jwts.SIG.HS256);
    }

    public static String createToken(MercureClaim mercureClaim, long expiration, String secret) {
        return createBuilder(mercureClaim, expiration, secret).compact();
    }

}
