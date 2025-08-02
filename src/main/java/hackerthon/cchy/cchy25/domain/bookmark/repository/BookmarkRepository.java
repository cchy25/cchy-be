package hackerthon.cchy.cchy25.domain.bookmark.repository;

import hackerthon.cchy.cchy25.domain.bookmark.entity.Bookmark;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByUserIdAndPolicyId(Long userId, Long policyId);

    Page<Bookmark> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT b.policy.id FROM Bookmark b WHERE b.user.id = :userId")
    Set<Long> findPolicyIdsByUserId(@Param("userId") Long userId);

}
