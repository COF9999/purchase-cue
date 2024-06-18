package com.project.restful.security;

import com.project.restful.enums.ConstantsJWT;
import com.project.restful.models.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private final String SECRETKEY = "ecea3241c575b7446ffde216fec4d1d92b9b1f98f8e49e8b3563abb70f6cab3e";

    public String extractUsername(String token){
        return extractClain(token,Claims::getSubject);
    }

    public Boolean isValid(String token, UserDetails user){
        String username = extractUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token){
        return extrateExpiration(token).before(new Date());
    }

    private Date extrateExpiration(String token){
        return extractClain(token,Claims::getExpiration);
    }

    public <T> T extractClain(String token, Function<Claims,T> resolve){
        Claims claims = extractAllClaims(token);
        return resolve.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(Users users){
        String token = Jwts
                .builder()
                .subject(users.getIdentification())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ConstantsJWT.ONEYEAR))
                .signWith(getSignKey())
                .compact();

        return token;
    }

    private SecretKey getSignKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRETKEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
