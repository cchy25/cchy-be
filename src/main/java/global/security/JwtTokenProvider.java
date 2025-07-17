package global.security;

import domain.auth.dto.JwtTokenDto;
import domain.user.service.CustomUserDetailsService;
import global.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements AuthenticationProvider {

    private final JwtProperties jwtProperties;
    private final Long ACCESS_TOKEN_EXPIRATION = 60 * 15L; // 초
    private final Long REFRESH_TOKEN_EXPIRATION = 60 * 60L; // 1시간
    private final Long REFRESH_TOKEN_LONG_EXPIRATION = 60 * 60 * 24 * 30L; // 30일
    private final CustomUserDetailsService customUserDetailsService;
//    private final AccessTokenBlackListRepository accessTokenBlackListRepository;

    public JwtTokenDto createToken(Long userId, boolean isLongTerm) {
        var accessToken = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setExpiration(
                        Date.from(Instant.now().plusSeconds(ACCESS_TOKEN_EXPIRATION))
                )
                .setSubject(userId.toString())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()) // 서명도 필요해요!
                .compact();

        var refreshToken = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setExpiration(
                        Date.from(Instant.now().plusSeconds(isLongTerm ?
                                REFRESH_TOKEN_LONG_EXPIRATION :
                                REFRESH_TOKEN_EXPIRATION))
                )
                .setSubject(userId.toString())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()) // 서명도 필요해요!
                .compact();

        return new JwtTokenDto(accessToken, refreshToken);
    }

    public Claims extractClaims(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSecretKey())
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            var userDetail = customUserDetailsService.loadUserByUsername((String) authentication.getCredentials());
            return new JwtAuthenticationToken(null, userDetail);
        } catch (UsernameNotFoundException ex) {
            throw new RuntimeException(); // TODO : 예외 처리 보강
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthenticationToken.class);
    }
}
