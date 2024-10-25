package com.project.restful.security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
@AllArgsConstructor
@Component
public class TokenValidation {

    private JwtService jwtService;

    public boolean validateSignatureToken(String token){
        try {
            Jwts.parser()
                    .verifyWith(jwtService.getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        }catch (SignatureException e){
            return false;
        }catch (Exception e){
            throw new IllegalArgumentException("Token Invalido");
        }
    }
}
