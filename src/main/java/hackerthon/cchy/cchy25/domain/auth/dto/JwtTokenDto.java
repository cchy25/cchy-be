package hackerthon.cchy.cchy25.domain.auth.dto;

import lombok.Builder;

@Builder
public record JwtTokenDto(
        String accessToken,
        String refreshToken,
        String signupToken
) {
}
