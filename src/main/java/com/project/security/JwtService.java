package com.project.security;

import com.project.exception.InvalidJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);


    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.prefix}")
    private String prefix;

    private final static String CLAIM_USER_ID = "userId";

    public String createToken(final Integer id, final String userEmail) {
        return Jwts.builder()
                .setSubject(userEmail)
                .claim(CLAIM_USER_ID, id)
                .setExpiration(createExpirationDate())
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public String getUserEmailFromToken(final String token) {
        try {
            final Claims claims = getClaimsFromToken(token);
            return claims.getSubject();
        } catch (Exception e) {
            throw new InvalidJwtException("Unable to parse token", e);
        }
    }


    Integer getUserIdFromToken(final String token) {
        try {
            final Claims claims = getClaimsFromToken(token);
            return Integer.parseInt((claims.get(CLAIM_USER_ID)).toString());
        } catch (Exception e) {
            throw new InvalidJwtException("Unable to parse token", e);
        }
    }

    Date getExpirationDateFromToken(final String token) {
        try {
            final Claims claims = getClaimsFromToken(token);
            return claims.getExpiration();
        } catch (Exception e) {
            throw new InvalidJwtException("Unable to parse token", e);
        }
    }

    boolean isTokenValid(final String token) {
        return !isTokenExpired(token);
    }

    private Boolean isTokenExpired(final String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date createExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000L);
    }

    private Claims getClaimsFromToken(final String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(cleanToken(token))
                    .getBody();
        } catch (Exception e) {
            throw new InvalidJwtException("Unable to parse token", e);
        }
    }

    private String cleanToken(final String token) {
        return token.replace(prefix, "").trim();
    }
}
