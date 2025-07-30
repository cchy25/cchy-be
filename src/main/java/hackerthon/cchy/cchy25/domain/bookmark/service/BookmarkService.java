package hackerthon.cchy.cchy25.domain.bookmark.service;

import hackerthon.cchy.cchy25.domain.bookmark.dto.BookmarkResponse;
import hackerthon.cchy.cchy25.domain.bookmark.entity.Bookmark;
import hackerthon.cchy.cchy25.domain.bookmark.repository.BookmarkRepository;
import hackerthon.cchy.cchy25.domain.policy.dto.PolicyResponse;
import hackerthon.cchy.cchy25.domain.policy.entity.Policy;
import hackerthon.cchy.cchy25.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private BookmarkRepository bookmarkRepository;

    @Transactional
    public BookmarkResponse bookmarkPolicy(Long userId, Long policyId) {
        boolean isBookmarked;
        LocalDateTime timestamp;

        var optionalBookmark = bookmarkRepository.findByUserIdAndTargetId(userId, policyId);

        if (optionalBookmark.isPresent()) {
            bookmarkRepository.delete(optionalBookmark.get());
            isBookmarked = false;
            timestamp = LocalDateTime.now();
        } else {
            var newBookmark = Bookmark.builder()
                    .user(User.builder().id(userId).build())
                    .policy(Policy.builder().id(policyId).build())
                    .build();
            var savedBookmark = bookmarkRepository.save(newBookmark);
            isBookmarked = true;
            timestamp = savedBookmark.getCreateAt();
        }
        return BookmarkResponse.builder()
                .current(isBookmarked)
                .when(timestamp)
                .build();
    }

    public Page<PolicyResponse> getBookmarkedPolicy(Long userId, Pageable pageable) {
        var policyResponse = bookmarkRepository.findByUserId(userId, pageable);

        return policyResponse.map(bookmark -> PolicyResponse.from(bookmark.getPolicy()));
    }
}
