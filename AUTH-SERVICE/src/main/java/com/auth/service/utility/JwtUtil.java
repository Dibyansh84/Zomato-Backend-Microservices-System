package com.auth.service.utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil
{
    //Secret Key used for JWT token signing and validation
    //Key length should be sufficiently strong for HS256 algorithm
    private static final String SECRET_KEY = "mysecretkeymysecretkeymysecretkey";

    public String generateToken(String username)
    {
        //Generate JWT token
        return Jwts.builder()

                //Set username/email as subject inside token
                .setSubject(username)

                //Set token creation time
                .setIssuedAt(new Date())

                //Set token expiration time
                //Token validity = 24 hours
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24
                )
                )

                //Sign token using secret key and HS256 algorithm
                .signWith(
                        Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256
                ).compact();
    }

    //If you're using secret key then Signature algorithm must be HS256.
    /* ES256	Private/Public Key
       RS256	RSA Keys */

    public boolean validateToken(String token)
    {
        try
        {
            //Parse and validate JWT token using secret key
            Jwts.parserBuilder()
                    //Set signing key for verification
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()
                    )
                    )

                    //Build JWT parser
                    .build()

                    //validate and parse token
                    .parseClaimsJws(token);

            //Return true if token is valid
            return true;

        }
        catch (Exception e)
        {
            //Return false if token is invalid
            //expired, malformed or tampered
            return false;
        }
    }
}
