package com.project.server.business.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class RefreshTokenUtil extends BaseJwtTokenUtil {
    private static final long serialVersionUID = -3301605591108950415L;

    private static final int lifetime = 10; //days

    @Value("security.refreshKey")
    private String secret;

    @Override
    protected Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(String username) {
        return doGenerateToken(username);
    }

    private String doGenerateToken(String subject) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, lifetime);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(calendar.getTime())
                .compact();
    }
}
