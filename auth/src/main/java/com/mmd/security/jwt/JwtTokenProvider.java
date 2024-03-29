package com.mmd.security.jwt;

import com.mmd.application.dto.TokenDto;
import com.mmd.repository.RefreshTokenRepository;
import com.mmd.exception.TokenExpiredException;
import com.mmd.exception.TokenNotValidException;
import com.mmd.entity.RefreshToken;
import com.mmd.security.MemberDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 30;        // 30일
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 30;  // 30일
    private static final String AUTHORITIES_KEY = "auth";
    private final Key key;
    private final MemberDetailsService memberDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    /* jwt secret key 변수 할당 */
    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey, MemberDetailsService memberDetailsService, RefreshTokenRepository refreshTokenRepository) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.memberDetailsService = memberDetailsService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    /* access/refresh 토큰 생성 */
    public TokenDto generate(String email, String authorities) {

        // access/refresh 토큰 설정
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);
        
        // 토큰 생성
        String accessToken = createToken(email, authorities, accessTokenExpiredAt);
        String refreshToken = createToken(email, authorities, refreshTokenExpiredAt);

        // refresh 토큰 redis에 저장
        refreshTokenRepository.save(RefreshToken.of(email, refreshToken, REFRESH_TOKEN_EXPIRE_TIME / 1000L));

        return TokenDto.of(accessToken, refreshToken, "Bearer", ACCESS_TOKEN_EXPIRE_TIME / 1000L);
    }

    /* access 토큰 재발급 */
    public TokenDto reissueAccessToken(String email, String authorities) {
        // refresh 토큰 유무
        RefreshToken refreshToken = refreshTokenRepository.findById(email)
                .orElseThrow(() -> new TokenExpiredException("존재하지 않는 만료된 refresh 토큰입니다."));

        // refresh token 유효성 검증
        // TODO : 2중으로 검증해줄 필요가 있을까? 토큰이 유효하지 않고...
        validateToken(refreshToken.getRefreshToken());
        Claims claims = parseClaims(refreshToken.getRefreshToken());
        if(!claims.getSubject().equals(email)) {
            throw new TokenNotValidException("로그인된 사용자의 refresh token이 아닙니다.");
        }

        // access 토큰 재발급
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = createToken(email, authorities, accessTokenExpiredAt);

        return TokenDto.of(accessToken, refreshToken.getRefreshToken(), "Bearer", ACCESS_TOKEN_EXPIRE_TIME / 1000L);
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
            throw new TokenNotValidException("유효하지 않은 토큰입니다.");
        } catch (ExpiredJwtException e){
            throw new TokenExpiredException("만료된 토큰입니다.");
        } catch (UnsupportedJwtException e){
            throw new TokenNotValidException("지원되지 않는 유형의 토큰입니다.");
        } catch (IllegalArgumentException e){
            throw new TokenNotValidException("클레임이 비어있습니다.");
        }
    }
    
    /* claim에 저장된 정보로 authentication 추출 */
    public Authentication extractAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        UserDetails memberDetails = memberDetailsService.loadUserByUsername(claims.getSubject());
        // TODO : 여기에서 memberDetails를 넘기면 Controller에서 member의 정보를 다 넘기게 되는데 이래도 되는걸까..? (memberDetails를 넘기지 않으면 @AuthenticationPrincipal이 null이 됨)
        return new UsernamePasswordAuthenticationToken(memberDetails, accessToken, memberDetails.getAuthorities());
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
