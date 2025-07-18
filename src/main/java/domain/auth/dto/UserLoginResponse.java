package domain.auth.dto;

import lombok.Builder;

@Builder
public class UserLoginResponse {
    private String accessToken;
}
