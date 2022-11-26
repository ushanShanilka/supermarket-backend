package com.supermarket.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 9/25/2022
 **/

@Component
public class JwtUtil {
    private String SECRET_KEY = "secret";

    public String extractPhoneNumber(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractType(String token) {
        Claims cl = extractAllClaims(token);

        return cl.get("type").toString();
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) throws ExpiredJwtException{
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) throws ExpiredJwtException{
        return extractExpiration(token).before(new Date());
    }

    //create JWT from userDetails
    public String generateToken(UserDetails userDetails, String userType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", userType);
        return createToken(claims, userDetails.getUsername(), userType);
    }

    private String createToken(Map<String, Object> claims, String subject, String userType) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    private String createRefreshToken(Map<String, Object> claims, String subject, String userType) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();

    }


    public Boolean validateToken(String token, UserDetails userDetails){
        final String phoneNumber = extractPhoneNumber(token);
        return (phoneNumber.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
}
