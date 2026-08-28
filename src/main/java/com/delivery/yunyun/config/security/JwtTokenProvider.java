package com.delivery.yunyun.config.security;

import com.delivery.yunyun.service.CustomerCustomService;
import com.delivery.yunyun.service.OwnerCustomService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final CustomerCustomService customerCustomService;
    private final OwnerCustomService ownerCustomService;

    @Value("${spring.jwt.secret}")
    private String secret;

    private SecretKey secretKey;

    private final long tokenValidMillisecond = 1000L * 60 * 60;

    @PostConstruct
    public void init(){
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String userId, List<String> roles){
        Date now = new Date();
        return Jwts.builder()
                .subject(userId)
                .claim("roles",roles)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(secretKey)
                .compact();
    }

    public List<String> getRoles(String token){
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return ((List<?>)claims.get("roles"))
                .stream()
                .map(String::valueOf)
                .toList();
    }

    public String getUsername(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Authentication getAuthentication(String token){
        String userName = getUsername(token);
        List<String> roles = getRoles(token);
        UserDetails userDetails;

        if(roles.contains("ROLE_USER")){
            userDetails = customerCustomService.loadUserByUsername(userName);
        }
        else if(roles.contains("ROLE_OWNER")){
            userDetails = ownerCustomService.loadUserByUsername(userName);
        }
        else{
            throw new RuntimeException("회원을 찾을 수 없습니다.");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, "",userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(token != null && token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token){
        try{
            Jws<Claims> claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return !claims.getPayload().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
