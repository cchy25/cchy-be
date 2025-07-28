package hackerthon.cchy.cchy25.global.security;

import hackerthon.cchy.cchy25.domain.auth.dto.JwtTokenDto;
import hackerthon.cchy.cchy25.global.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();

        var refreshToken = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setExpiration(
                        Date.from(Instant.now().plusSeconds(isLongTerm ?
                                REFRESH_TOKEN_LONG_EXPIRATION :
                                REFRESH_TOKEN_EXPIRATION))
                )
                .setSubject(userId.toString())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();

        return new JwtTokenDto(accessToken, refreshToken, null);
    }

    public JwtTokenDto createSignupToken(String socialId) {
        var signupToken =  Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setExpiration(
                        Date.from(Instant.now().plusSeconds(ACCESS_TOKEN_EXPIRATION))
                )
                .setSubject(socialId)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
        return new JwtTokenDto(null, null, signupToken);
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
        var userId = extractClaims((String) authentication.getCredentials()).getSubject();
        var userDetail = customUserDetailsService.loadUserByUsername(userId);
        return new JwtAuthenticationToken(null, userDetail);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthenticationToken.class);
    }
}
