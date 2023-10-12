package com.roadmaker.lucid.member.authentication;

import com.roadmaker.lucid.member.dto.TokenInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.*;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j @Component
public class JwtProvider {
    private final SecretKey key; //Key가 SecretKey, PublicKey로 구분됨
    private static final long TOKEN_VALID_TIME = 1000L * 60 * 60 * 24 * 7;

    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenInfo generateToken(Authentication authentication) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        Date accessTokenExpirationIn = new Date(now + TOKEN_VALID_TIME);

        //access token 생성
        String accessToken = Jwts.builder()
                .subject(authentication.getName())
                .claim("auth", authorities)
                .expiration(accessTokenExpirationIn)
                .signWith(key, Jwts.SIG.HS256)
                .compact();

        //refresh token 생성
        String refreshToken = Jwts.builder()
                .expiration(new Date(now + TOKEN_VALID_TIME))
                .signWith(key, Jwts.SIG.HS256)
                .compact();

        TokenInfo tokeninfo = TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        System.out.println(tokeninfo);
        return tokeninfo;
    }

    public Authentication getAuthentication(String accessToken) {

        Claims claims = parseClaims(accessToken);
        if(claims.get("auth") == null) throw new RuntimeException("권한 정보가 없는 토큰입니다");

        //클레임에서 권한 정보 가져옴
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList();

        //UserDetails를 만들어 Authentication리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    //토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT Claims String is empty", e);
        }
        return false;
    }

    // 토큰을 분해하여 내용물을 확인하는 메서드: 들어온 리퀘스트에서 헤더가 Authentication인 부분을 발췌,
    // 그 중에서 Bearer로 시작하는 토큰을 가져온다: Bearer 가 6 글자이므로 한칸 띄고 시작하는 index 7 부터
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(accessToken).getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
