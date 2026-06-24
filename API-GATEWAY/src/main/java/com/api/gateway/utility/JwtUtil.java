package com.api.gateway.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil
{
    //Secret key used for signing and validating JWT token
    //Must be sufficiently long for HMAC SHA algorithm
    private static final String SECRET_KEY = "mysecretkeymysecretkeymysecretkey";

    public boolean validateToken(String token)
    {
        try
        {
            //Parse and validate JWT token using secret key
            Claims claims = Jwts.parserBuilder()

                    //Set signing key for token verification
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()
                    )
                    )

                    //Build JWT parser
                    .build()
                    //Parse JWT token
                    .parseClaimsJws(token)
                    //Extract claims/body from token
                    .getBody();

            //Return true if subject(username/email) exists in taken
            return claims.getSubject() !=null;
        }
        catch(Exception e)
        {
            //Return false if token is invalid, expired, or malinformed
            return false;
        }
    }
}
