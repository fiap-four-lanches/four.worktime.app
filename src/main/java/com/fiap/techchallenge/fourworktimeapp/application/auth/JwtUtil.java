package com.fiap.techchallenge.fourworktimeapp.application.auth;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.Employee;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final long ACCESS_TOKEN_VALIDITY = 15 * 60 * 1000;
    private final String secret_key = "bXlzZWNyZXRrZXktbXlzZWNyZXRrZXktbXlzZWNyZXRrZXktbXlzZWNyZXRrZXk=";
    private final JwtParser jwtParser;

    public JwtUtil() {
        byte[] keyBytes = Base64.getDecoder().decode(secret_key);
        SecretKey verifyKey = Keys.hmacShaKeyFor(keyBytes);
        this.jwtParser = Jwts.parser().verifyWith(verifyKey).build();
    }

    public String createToken(Employee employee) {
        var claims = Jwts.claims().subject(employee.getRegistry()).build();
        var tokenCreateTime = new Date();
        var tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(ACCESS_TOKEN_VALIDITY));
        return Jwts.builder()
                .claims(claims)
                .expiration(tokenValidity)
                .signWith(getSignKey(), Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secret_key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims parseJwtClaims(String token) {
        return jwtParser.parseSignedClaims(token).getPayload();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            throw e;
        }
    }
}
