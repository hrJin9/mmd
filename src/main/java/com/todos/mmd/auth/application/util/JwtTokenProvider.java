package com.todos.mmd.auth.application.util;

import com.todos.mmd.auth.api.response.TokenResponse;
import com.todos.mmd.auth.application.UserDetailsServiceImpl;
import com.todos.mmd.global.exception.AuthException;
import com.todos.mmd.repository.redis.RedisRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.security.auth.Subject;
import java.security.Key;
import java.time.Duration;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24;       // 24시간
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일
    private static final String AUTHORITIES_KEY = "auth";
    private final Key key;
    private final UserDetailsServiceImpl userDetailsService;
    private final RedisRepository redisRepository;

    /* jwt secret key 변수 할당 */
    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey, UserDetailsServiceImpl userDetailsService, RedisRepository redisRepository) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userDetailsService = userDetailsService;
        this.redisRepository = redisRepository;
    }

    /* 토큰 생성 */
    public TokenResponse generate(String email, String authorities) {

        // access/refresh 토큰 설정
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);
        
        // 토큰 생성
        String accessToken = createToken(email, authorities, accessTokenExpiredAt);
        String refreshToken = createToken(email, authorities, refreshTokenExpiredAt);

        // refresh 토큰 redis에 저장
        redisRepository.setValues(email, refreshToken, Duration.ofMillis(ACCESS_TOKEN_EXPIRE_TIME));

        return TokenResponse.of(accessToken, refreshToken, "Bearer", ACCESS_TOKEN_EXPIRE_TIME / 1000L);
    }

    /* 토큰 재발급 */
    public TokenResponse reissueAccessToken(String email, String authorities) {
        String refreshTokenInRedis = redisRepository.getValues(email);
        if(!StringUtils.hasText(refreshTokenInRedis)) {
            throw new AuthException("만료된 인증 정보입니다.");
        }
        return generate(email, authorities);
    }

    /* 토큰 빌드 */
    public String createToken(String email, String authorities, Date expiredAt) {
        return Jwts.builder()
                .setSubject(email)
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /* 토큰 유효성 검증 */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid Jwt Token");
        } catch (ExpiredJwtException e){
            log.info("Expired Jwt Token");
        } catch (UnsupportedJwtException e){
            log.info("Unsupported Jwt Token");
        } catch (IllegalArgumentException e){
            log.info("Claim is empty");
        }
        return false;
    }
    
    /* claim에 저장된 정보로 authentication 추출 */
    public Authentication extractAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        UserDetails member = userDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(member.getUsername(), accessToken, member.getAuthorities());
    }
    
    /* 토큰 Parsing */
    private Claims parseClaims(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }

}
