package hackerthon.cchy.cchy25.domain.auth.controller;

import hackerthon.cchy.cchy25.domain.auth.dto.*;
import hackerthon.cchy.cchy25.domain.auth.service.AuthService;
import hackerthon.cchy.cchy25.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @GetMapping("/auth/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal UserDetails user){
        log.info("me={}",user.getUsername());
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/auth/health")
    public ResponseEntity<?> health() {
        log.info("test");
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> localLogin(@RequestBody UserLoginRequest userLoginRequest) {
        var jwtTokenDto = authService.login(userLoginRequest);
        var refreshTokenCookie = ResponseCookie.from("refreshToken", jwtTokenDto.refreshToken())
//                .httpOnly(true)
//                .secure(true)
                .path("/api/v1/auth")
                .sameSite("None")
                .maxAge(userLoginRequest.isLongTerm()? Duration.ofDays(30) : Duration.ofHours(1))
                .build();

        var userLoginResponse = UserLoginResponse.builder()
                .accessToken(jwtTokenDto.accessToken())
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(userLoginResponse);
    }

    @PostMapping("/auth/social-login")
    public ResponseEntity<?> socialLogin(@RequestBody UserLoginRequest userLoginRequest) {
        var jwtTokenDto = authService.socialLogin(userLoginRequest);
        var refreshTokenCookie = ResponseCookie.from("refreshToken", jwtTokenDto.refreshToken())
//                .httpOnly(true)
//                .secure(true)
                .path("/api/v1/auth")
                .sameSite("None")
                .maxAge(userLoginRequest.isLongTerm()? Duration.ofDays(30) : Duration.ofHours(1))
                .build();

        var userLoginResponse = UserLoginResponse.builder()
                .accessToken(jwtTokenDto.accessToken())
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(userLoginResponse);
    }



    @PostMapping("/auth/signup")
    public ResponseEntity<?> signup(@RequestBody UserSignUpRequest userSignUpRequest) {
        var jwtTokenDto = authService.signup(userSignUpRequest);

        var userLoginResponse = UserLoginResponse.builder()
                .accessToken(jwtTokenDto.accessToken())
                .build();

        var refreshTokenCookie = ResponseCookie.from("refreshToken", jwtTokenDto.refreshToken())
//                .httpOnly(true)
//                .secure(true)
                .path("/api/v1/auth")
                .sameSite("None")
                .maxAge(Duration.ofHours(1))
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(userLoginResponse);
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(
            @AuthenticationPrincipal User user,
            HttpServletRequest request
    ) {
//        authService.logout(user.getId(), accessToken, refreshToken);
//        var accessTokenCookie = ResponseCookie.from("accessToken", accessToken)
//                .httpOnly(true)
//                .secure(true)
//                .path("/")
//                .sameSite("None")
//                .maxAge(0)
//                .build();
//        var refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken)
//                .httpOnly(true)
//                .secure(true)
//                .path("/")
//                .sameSite("None")
//                .maxAge(0)
//                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
//                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
//                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .build();
    }

    @PostMapping("/auth/reissue")
    public ResponseEntity<?> reissue(
            @AuthenticationPrincipal User user,
            @CookieValue("refreshToken") String refreshToken
    ) {
//        var jwtTokenDto = authService.reissue(refreshToken);
//        var accessTokenCookie = ResponseCookie.from("accessToken", jwtTokenDto.accessToken())
//                .httpOnly(true)
//                .secure(true)
//                .path("/")
//                .sameSite("None")
//                .maxAge(Duration.ofMinutes(15))
//                .build();
//        var refreshTokenCookie = ResponseCookie.from("refreshToken", jwtTokenDto.refreshToken())
//                .httpOnly(true)
//                .secure(true)
//                .path("/")
//                .sameSite("None")
//                .maxAge(Duration.ofHours(1))
//                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
//                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
//                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .build();
    }

    @PostMapping("/auth/check-email")
    public ResponseEntity<?> checkEmailUniqueness(
            @RequestBody EmailRequest emailRequest
    ) {
//        authService.checkEmailUniqueness(emailRequest.email());
        return ResponseEntity.ok(null);
    }

    @PostMapping("/auth/check-username")
    public ResponseEntity<?> checkUsernameUniqueness(
            @RequestBody UsernameRequest usernameRequest
    ) {
//        authService.checkUsernameUniqueness(usernameRequest.username());
        return ResponseEntity.ok(null);
    }

    @PostMapping("/auth/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestBody PasswordResetRequest passwordResetRequest
    ) {
//        authService.resetPassword(passwordResetRequest);
        return ResponseEntity.ok(null);
    }
}
