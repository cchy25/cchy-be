package hackerthon.cchy.cchy25.domain.bookmark.controller;

import hackerthon.cchy.cchy25.domain.bookmark.dto.BookmarkResponse;
import hackerthon.cchy.cchy25.domain.bookmark.service.BookmarkService;
import hackerthon.cchy.cchy25.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Slf4j
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/bookmarks/{policyId}/toggle")
    public ResponseEntity<BookmarkResponse> bookmarkPolicy(
            @AuthenticationPrincipal User user,
            @PathVariable("policyId") Long policyId
    ) {
        return ResponseEntity.ok(bookmarkService.bookmarkPolicy(user.getId(), policyId));
    }
}
