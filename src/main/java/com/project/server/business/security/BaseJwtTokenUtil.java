package com.project.server.business.security;

import io.jsonwebtoken.Claims;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

public abstract class BaseJwtTokenUtil implements Serializable {

    public String getSubject(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String getAudienceFromToken(String token) {
        return getClaimFromToken(token, Claims::getAudience);
    }

    public Boolean validateToken(String token, String subject) {
        final String claim = getSubject(token);
        return claim.equals(subject);
    }

    private  <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    protected abstract Claims getAllClaimsFromToken(String token);

}

