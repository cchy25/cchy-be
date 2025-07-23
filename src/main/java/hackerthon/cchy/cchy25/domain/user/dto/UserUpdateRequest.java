package hackerthon.cchy.cchy25.domain.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class UserUpdateRequest {

    private String username;
}
