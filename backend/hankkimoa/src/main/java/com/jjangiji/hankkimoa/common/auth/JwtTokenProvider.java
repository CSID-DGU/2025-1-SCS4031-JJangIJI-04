package com.jjangiji.hankkimoa.common.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final String secretKey;
    private Key SECRET_KEY;
    private final long expiration;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration}") int expiration) {
        this.secretKey = secretKey;
        this.expiration = expiration;
        this.SECRET_KEY = new SecretKeySpec(java.util.Base64.getDecoder().decode(secretKey), SignatureAlgorithm.HS512.getJcaName());
    }

    //access token 생성
    public String createAccessToken(String socialId, String role){
        Claims claims = Jwts.claims().setSubject(socialId);
        claims.put("role", role);
        Date now = new Date();
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+ expiration))
                .signWith(SECRET_KEY)
                .compact();
        return accessToken;
    }

    //refresh token 생성
    public String createRefreshToken(String socialId, String role){
        Claims claims = Jwts.claims().setSubject(socialId);
        claims.put("role", role);
        Date now = new Date();
        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+ expiration *24*14))
                .signWith(SECRET_KEY)
                .compact();
        return refreshToken;
    }

    //토큰 유효기간 검증
    public boolean isExpired(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            Date expiration = claims.getExpiration();
            return new Date().after(expiration);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return true;
        }

    }

}
