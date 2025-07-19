package hackerthon.cchy.cchy25.domain.auth.repository;

import hackerthon.cchy.cchy25.domain.auth.entity.WhitelistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenWhitelistRepository extends JpaRepository<WhitelistItem, String> {
}
