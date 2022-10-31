package com.retmix.shop.shop.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JWTProvider {

    private final UserDetailsService userDetailsService;

    @Value("${jwt.header}")
    private String authHeader;

    @Autowired
    public JWTProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String createToken(String email, String role){
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);
        Date nowCreate = new Date(System.currentTimeMillis());
        Date expired = new Date(nowCreate.getTime()+10*24*60*60*1000); //1 hour

        return Jwts.builder().setClaims(claims)
                .setIssuedAt(nowCreate)
                .setExpiration(expired)
                .signWith(SignatureAlgorithm.HS256, "negr")
                .compact();
    }

    public boolean validationToken(String token) throws JwtAuthException {
        try{
            Jws<Claims> jwsClaims = Jwts.parser().setSigningKey("negr").parseClaimsJws(token);
            return !jwsClaims.getBody().getExpiration().before(new Date());
        }catch (JwtException | IllegalArgumentException jwtException){
            throw new JwtAuthException("JWT token is expired", HttpStatus.UNAUTHORIZED);
        }

    }

    public Authentication getAuth(String token){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getEmail(String token){
        return Jwts.parser().setSigningKey("negr").parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request){
        return request.getHeader(authHeader);
    }
}
