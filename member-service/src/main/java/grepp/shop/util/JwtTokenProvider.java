package grepp.shop.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    @Value("${token.secret}")
    private String secret;

    private static final long JWT_EXPIRATION_MS = 86400000L * 7;

    public String generateToken(Authentication authentication) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + JWT_EXPIRATION_MS);
        return Jwts.builder()
                .subject((String) authentication.getPrincipal())
                .issuedAt(now)
                .expiration(expiredDate)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public void validateToken(String token) {
        getUserData(token);
    }

    public String getUserData(String token) {
        try {
            return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            throw new JwtException("Expired token");
        } catch (JwtException e) {
            throw new JwtException("JWT error");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
