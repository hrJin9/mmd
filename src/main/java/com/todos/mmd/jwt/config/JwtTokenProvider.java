package com.todos.mmd.jwt.config;

import com.todos.mmd.jwt.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String GRANT_TYPE = "Bearer";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final String secret;
    private final long tokenValidityInMilliseconds;
    private Key key;

    /* jwt 정보 주입 */
    public JwtTokenProvider(@Value("${jwt.secret}") String secret,
                            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInMilliseconds){
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
    }

    /* 주입받은 secretKey로 key 변수 할당 */
    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("TokenProvider.afterPropertiesSet");

        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /* Request Header에서 토큰 정보 꺼내오기 */
    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /* authentication 권한정보 -> 토큰생성 */
    public TokenDto createToken(Authentication authentication){
        log.debug("TokenProvider.createToken");

        // 권한 -> claim 설정
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // 토큰 만료시간 설정
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        // Access Token
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();

        return TokenDto.builder()
                .grantType(GRANT_TYPE)
                .accessToken(accessToken)
                .build();
    }


    /* 토큰정보로 authentication 리턴 */
    public Authentication getAuthentication(String token){
        log.debug("TokenProvider.getAuthentication");

        // 토큰으로 claim 생성
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // 토큰으로 authorities 생성
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // claim과 authorities로 User 객체 생성
        User principal = new User(claims.getSubject(), "", authorities);

        // Authentication 객체 리턴
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }


    /* 토큰 유효성 검증 */
    public boolean validateToken(String token){
        log.debug("TokenProvider.validateToken");
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 Jwt 토큰 서명");
        } catch (ExpiredJwtException e){
            log.info("만료된 Jwt 토큰");
        } catch (UnsupportedJwtException e){
            log.info("지원되지 않는 Jwt 토큰");
        } catch (IllegalArgumentException e){
            log.info("잘못된 Jwt 토큰");
        }
        return false;
    }

}
