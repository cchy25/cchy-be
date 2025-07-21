package hackerthon.cchy.cchy25.domain.auth.dto;

import lombok.Builder;

@Builder
public record UserLoginRequest(
        SocialType loginType,
        String code,
        String email,
        String password,
        Boolean isLongTerm
) {
}
