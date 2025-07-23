package hackerthon.cchy.cchy25.domain.user.controller;

import hackerthon.cchy.cchy25.domain.user.dto.UserUpdateRequest;
import hackerthon.cchy.cchy25.domain.user.entity.User;
import hackerthon.cchy.cchy25.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/me")
    public ResponseEntity<?> me(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(userService.me(user.getId()));
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateUser(
            @AuthenticationPrincipal User user,
            @RequestBody UserUpdateRequest userUpdateRequest
    ) {
        userService.update(user.getId(), userUpdateRequest);
        return ResponseEntity.ok().build();
    }
}
