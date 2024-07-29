package com.iocl.dispatchapplication.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.iocl.dispatchapplication.model.MstEmployee;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {
	
	private static final Logger logger=LoggerFactory.getLogger(JwtUtils.class);
	 @Value("${jwtSecret}")
     private String jwtSecret;//private String jwtSecret; 

     @Value("${jwtExpirationMs}")
     private int jwtExpirationMs;
     
     @Value("${jwtCookieName}")
     private String jwtCookie;
     
     // Generates a JWT token for the given userName.
     public String generateToken(String userName,Map<String, Object> claims) {
    	 claims.put("locCode", claims.get("locCode"));
         return Jwts.builder()
                 .setClaims(claims)
                 .setSubject(userName)
                 .setIssuedAt(new Date(System.currentTimeMillis()))
                 .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) 
                 .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
     }


     // Creates a signing key from the base64 encoded secret.
     //returns a Key object for signing the JWT.
     private Key getSignKey() {
         // Decode the base64 encoded secret key and return a Key object
         byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
         return Keys.hmacShaKeyFor(keyBytes);
     }
     public String extractUserName(String token) {
         return extractAllClaims(token).getSubject();
     }

     public String extractUserId(String token) {
         return extractAllClaims(token).get("userId", String.class);
     }

     public String extractLocationName(String token) {
         return extractAllClaims(token).get("locationName", String.class);
     }

     public String extractLocCode(String token) {
         return extractAllClaims(token).get("locCode", String.class);
     }


     
     //Extracts all claims from the JWT token.
     //return-> Claims object containing all claims.
     private Claims extractAllClaims(String token) {
         // Parse and return all claims from the token
         return Jwts.parserBuilder()
                 .setSigningKey(getSignKey())
                 .build().parseClaimsJws(token).getBody();
     }


    
     public boolean validateJwtToken(String authToken) {
         try {
             Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
             return true;
         } catch (SignatureException e) {
             logger.error("Invalid JWT signature: {}", e.getMessage());
         } catch (MalformedJwtException e) {
             logger.error("Invalid JWT token: {}", e.getMessage());
         } catch (ExpiredJwtException e) {
             logger.error("JWT token is expired: {}", e.getMessage());
         } catch (UnsupportedJwtException e) {
             logger.error("JWT token is unsupported: {}", e.getMessage());
         } catch (IllegalArgumentException e) {
             logger.error("JWT claims string is empty: {}", e.getMessage());
         }

         return false;
     }
     
     public String getJwtFromCookies(HttpServletRequest request) {
         Cookie[] cookies = request.getCookies();
         if (cookies != null) {
             for (Cookie cookie : cookies) {
                 if (cookie.getName().equals(jwtCookie)) {
                     String value = cookie.getValue();
                     if (value.contains(" ")) {
                         logger.error("Cookie value contains invalid characters (spaces): {}", value);
                         return null;
                     }
                     return value;
                 }
             }
         }
         return null;
     }
   
     public ResponseCookie createJwtCookie(String jwt) {
         if (jwt == null || jwt.trim().isEmpty()) {
             throw new IllegalArgumentException("JWT is null or empty");
         }

         jwt = jwt.trim(); // Ensure no extra spaces

         // Log the cleaned JWT
         logger.info("Cleaned JWT before creating cookie: '{}'", jwt);

         return ResponseCookie.from(jwtCookie, jwt)
                 .httpOnly(true)
                 .secure(true) // Set this to true in production for secure HTTPS cookies
                 .maxAge(24 * 60 * 60) // 1 day expiry
                 .path("/")
                 .build();
     } 

       public ResponseCookie getCleanJwtCookie() {
    	   logger.info("cleaned jwt cookie for signout");
           return ResponseCookie.from(jwtCookie, "")
                   .httpOnly(true)
                   .secure(true) // Set this to true in production for secure HTTPS cookies
                   .maxAge(0) // Expire the cookie immediately
                   .path("/")
                   .build();
       }

//       public String generateJwtToken(Authentication authentication) {
//           UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
//           return generateToken(userPrincipal.getUsername());
//       }

    
     

}
