package hackerthon.cchy.cchy25.domain.user.dto;

import hackerthon.cchy.cchy25.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {
    private String username;
    private String email;


    public static UserResponse from(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
