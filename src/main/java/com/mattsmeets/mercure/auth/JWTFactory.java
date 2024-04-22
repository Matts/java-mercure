package com.mattsmeets.mercure.auth;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JWTFactory {

    public static JwtBuilder createBuilder(MercureClaim mercureClaim, long expiration, String secret) {
        Map<String, Object> jwtClaims = new HashMap<>();
        jwtClaims.put("mercure", mercureClaim);

        return Jwts.builder()
                .setClaims(jwtClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(expiration))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .setHeaderParam("typ", "JWT");
    }

    public static String createToken(MercureClaim mercureClaim, long expiration, String secret) {
        return createBuilder(mercureClaim, expiration, secret).compact();
    }

}
