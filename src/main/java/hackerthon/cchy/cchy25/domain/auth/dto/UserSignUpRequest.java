package hackerthon.cchy.cchy25.domain.auth.dto;

import lombok.Builder;

@Builder
public record UserSignUpRequest(
        String email,
        String username,
        String password,
        String confirmPassword,
        SocialType socialType,
        String signupToken
) {
}
