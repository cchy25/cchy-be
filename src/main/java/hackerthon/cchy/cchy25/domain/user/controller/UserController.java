package hackerthon.cchy.cchy25.domain.user.controller;

import hackerthon.cchy.cchy25.domain.bookmark.service.BookmarkService;
import hackerthon.cchy.cchy25.domain.policy.dto.PolicyResponse;
import hackerthon.cchy.cchy25.domain.user.dto.UserResponse;
import hackerthon.cchy.cchy25.domain.user.dto.UserUpdateRequest;
import hackerthon.cchy.cchy25.domain.user.entity.User;
import hackerthon.cchy.cchy25.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BookmarkService bookmarkService;

    @SecurityRequirement(name = "Authorization")
    @GetMapping("/user/me")
    public ResponseEntity<UserResponse> me(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(userService.me(user.getId()));
    }

    @SecurityRequirement(name = "Authorization")
    @PutMapping("/user/me")
    public ResponseEntity<Void> updateUser(
            @AuthenticationPrincipal User user,
            @RequestBody UserUpdateRequest userUpdateRequest
    ) {
        userService.update(user.getId(), userUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/me/bookmarks")
    public ResponseEntity<Page<PolicyResponse>> getMyBookmarks(
            @AuthenticationPrincipal User user,
            Pageable pageable
    ) {
        bookmarkService.getBookmarks(user.getId(), pageable);
    }
}
