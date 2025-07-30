package hackerthon.cchy.cchy25.domain.bookmark.repository;

import hackerthon.cchy.cchy25.domain.bookmark.entity.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByUserIdAndPolicyId(Long userId, Long policyId);

    Page<Bookmark> findByUserId(Long userId, Pageable pageable);
}
