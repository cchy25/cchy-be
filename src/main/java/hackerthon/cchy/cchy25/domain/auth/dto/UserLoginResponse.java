package hackerthon.cchy.cchy25.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserLoginResponse {
    private String accessToken;
    private String signupToken;

}
