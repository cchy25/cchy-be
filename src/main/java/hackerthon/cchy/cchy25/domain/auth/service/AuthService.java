package hackerthon.cchy.cchy25.domain.auth.service;

import hackerthon.cchy.cchy25.domain.auth.dto.*;
import hackerthon.cchy.cchy25.domain.auth.entity.SocialAccount;
import hackerthon.cchy.cchy25.domain.auth.exception.AuthenticationErrorCode;
import hackerthon.cchy.cchy25.domain.auth.exception.AuthenticationException;
import hackerthon.cchy.cchy25.domain.auth.repository.AccessTokenBlackListRepository;
import hackerthon.cchy.cchy25.domain.auth.repository.RefreshTokenWhitelistRepository;
import hackerthon.cchy.cchy25.domain.auth.repository.SocialAccountRepository;
import hackerthon.cchy.cchy25.domain.user.entity.User;
import hackerthon.cchy.cchy25.domain.user.repository.UserRepository;
import hackerthon.cchy.cchy25.global.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final SocialAccountRepository socialAccountRepository;
    private final AccessTokenBlackListRepository accessTokenBlackListRepository;
    private final RefreshTokenWhitelistRepository refreshTokenWhiteListRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final Map<SocialType, OauthService> oauthServiceStrategies;

    public AuthService(UserRepository userRepository, SocialAccountRepository socialAccountRepository, AccessTokenBlackListRepository accessTokenBlackListRepository, RefreshTokenWhitelistRepository refreshTokenWhiteListRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, List<OauthService> oauthServices) {
        this.userRepository = userRepository;
        this.socialAccountRepository = socialAccountRepository;
        this.accessTokenBlackListRepository = accessTokenBlackListRepository;
        this.refreshTokenWhiteListRepository = refreshTokenWhiteListRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.oauthServiceStrategies = oauthServices.stream()
                .collect(Collectors.toMap(
                        strategy -> strategy.getLoginType(),
                        strategy -> strategy
                ));
    }

    @Transactional
    public JwtTokenDto login(UserLoginRequest userLoginRequest) {
        var foundedUser = userRepository.findByEmail(userLoginRequest.email())
                .orElseThrow(() -> new AuthenticationException(AuthenticationErrorCode.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(userLoginRequest.password(), foundedUser.getPassword())) {
            throw new AuthenticationException(AuthenticationErrorCode.INVALID_CREDENTIALS);
        }
        var jwtTokenDto = jwtTokenProvider.createToken(foundedUser.getId(), userLoginRequest.isLongTerm());

//        var claims = jwtTokenProvider.extractClaims(jwtTokenDto.refreshToken());

//        refreshTokenWhiteListRepository.save(WhitelistItem.builder()
//                .jti(claims.getId())
//                .expiredAt(claims.getExpiration())
//                .build()
//        );

        return jwtTokenDto;
    }

    public JwtTokenDto socialLogin(UserLoginRequest userLoginRequest) {
        var oauthService= oauthServiceStrategies.get(userLoginRequest.loginType());
        var token = oauthService.getToken(userLoginRequest.code());
        var socialId = oauthService.getSocialId(token);

        var socialAccount = socialAccountRepository.findBySocialId(socialId);

        // 소셜 계정을 찾을 수 없다면 회원가입
        if (socialAccount.isEmpty()) {
            return jwtTokenProvider.createSignupToken(socialId);
        }

        var foundUser = socialAccount.get().getUser();

        return jwtTokenProvider.createToken(foundUser.getId(), false);
    }

    @Transactional
    public JwtTokenDto signup(UserSignUpRequest userSignUpRequest) {

        // 소셜 로그인 처리
        if (userSignUpRequest.socialType() != SocialType.LOCAL && userSignUpRequest.signupToken() == null) {
            throw new AuthenticationException(AuthenticationErrorCode.INVALID_CREDENTIALS); //TODO : 예외 수정
        }

        // 유저네임 증복 검증
        if (userRepository.existsByUsername(userSignUpRequest.username())) {
            throw new AuthenticationException(AuthenticationErrorCode.DUPLICATE_USERNAME);
        }

        // 이메일 중복 검증
        if (userRepository.existsByEmail(userSignUpRequest.email())) {
            throw new AuthenticationException(AuthenticationErrorCode.DUPLICATE_EMAIL);
        }

        // 확인 비밀번호 검증
        if (!userSignUpRequest.password().equals(userSignUpRequest.confirmPassword())) {
            throw new AuthenticationException(AuthenticationErrorCode.CONFIRM_PASSWORD_MISMATCH);
        }

        var newUser = userRepository.save(User.builder()
                .username(userSignUpRequest.username())
                .password(passwordEncoder.encode(userSignUpRequest.password()))
                .email(userSignUpRequest.email())
                .build());

        if (userSignUpRequest.signupToken() != null) {
            var socialAccount = socialAccountRepository.save(SocialAccount.builder()
                    .socialId(userSignUpRequest.signupToken())
                    .build());
            newUser.addSocialAccount(socialAccount);
        }

        return jwtTokenProvider.createToken(newUser.getId(), false);
    }



//
//    @Transactional
//    public void logout(Long userId, String accessToken, String refreshToken) {
//        Instant now = Instant.now();
//        var refreshTokenClaims = jwtTokenProvider.extractClaims(refreshToken);
//        var accessTokenClaims = jwtTokenProvider.extractClaims(accessToken);
//
//        refreshTokenWhiteListRepository.deleteById(refreshTokenClaims.getId());
//        accessTokenBlackListRepository.save(BlacklistItem.builder() // jpa 아니므로 예외처리 필요
//                .jti(accessTokenClaims.getId())
//                .ttl(Duration.between(now, accessTokenClaims.getExpiration().toInstant()).getSeconds())
//                .build());
//    }
//
//    @Transactional
//    public JwtTokenDto reissue(String refreshToken) {
//        var refreshTokenClaims = jwtTokenProvider.extractClaims(refreshToken);
//        var refreshTokenId = refreshTokenClaims.getId();
//        var userId = Long.parseLong(refreshTokenClaims.getSubject());
//        var whitelistItem= refreshTokenWhiteListRepository.findById(refreshTokenId)
//                .orElseThrow(() -> new AuthenticationException(AuthenticationErrorCode.EXPIRED_TOKEN));
//
//        if (whitelistItem.getExpiredAt().before(new Date())) {
//            throw new AuthenticationException(AuthenticationErrorCode.EXPIRED_TOKEN);
//        }
//
//        refreshTokenWhiteListRepository.delete(whitelistItem);
//        var jwtTokenDto = jwtTokenProvider.createToken(userId, false);
//        var claims = jwtTokenProvider.extractClaims(jwtTokenDto.refreshToken());
//        var now = Instant.now();
//        refreshTokenWhiteListRepository.save(WhitelistItem.builder()
//                .jti(claims.getId())
//                .expiredAt(claims.getExpiration())
//                .build()
//        );
//
//        // 만료처리 안해도 되는가? 계속 리이슈하면 토큰 무제한 발급가능해진다. -> 그냥 409처리하는게 더 옮은 방향이라 생각된다.
////        var accessTokenClaims = jwtTokenProvider.extractClaims(accessToken);
////        accessTokenBlackListRepository.save(BlacklistItem.builder() // 기존 엑세스 토큰 만료, 실패 예외 필요
////                .jti(accessTokenClaims.getId())
////                .ttl(Duration.between(now, accessTokenClaims.getExpiration().toInstant()).getSeconds())
////                .build()
////        );
//
//        return jwtTokenDto;
//    }
//
//    public void checkEmailUniqueness(String email) {
//        if (userRepository.existsByEmail(email)) {
//            throw new AuthenticationException(AuthenticationErrorCode.DUPLICATE_EMAIL);
//        }
//    }
//
//    public void checkUsernameUniqueness(String username) {
//        if (userRepository.existsByEmail(username)) {
//            throw new AuthenticationException(AuthenticationErrorCode.DUPLICATE_USERNAME);
//        }
//    }
//
//    public void resetPassword(PasswordResetRequest passwordResetRequest) {
//
//    }
//
//    public void findUsername(String email) {
//        var FoundUser = userRepository.findByEmail(passwordEncoder.encode(email))
//                .orElseThrow(() -> new AuthenticationException(AuthenticationErrorCode.DUPLICATE_USERNAME));
//    }
}
