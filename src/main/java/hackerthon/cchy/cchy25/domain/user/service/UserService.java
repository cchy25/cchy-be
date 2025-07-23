package hackerthon.cchy.cchy25.domain.user.service;

import hackerthon.cchy.cchy25.domain.user.dto.UserResponse;
import hackerthon.cchy.cchy25.domain.user.dto.UserUpdateRequest;
import hackerthon.cchy.cchy25.domain.user.entity.User;
import hackerthon.cchy.cchy25.domain.user.exception.UserErrorCode;
import hackerthon.cchy.cchy25.domain.user.exception.UserException;
import hackerthon.cchy.cchy25.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserResponse me(Long userId) {
        return UserResponse.from(
                userRepository.findById(userId).orElseThrow(
                        () -> new UserException(UserErrorCode.NOT_FOUND))
        );
    }

    public void update(Long userId, UserUpdateRequest userUpdateRequest) {
        var foundUser = userRepository.findById(userId)
                        .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));

        updateUsernameIfPresent(foundUser, userUpdateRequest.getUsername());

        userRepository.save(foundUser);
    }

    private void updateUsernameIfPresent(User user, String username) {
        if(username != null && !username.isBlank()) {
            user.setUsername(username);
        }
    }
}
