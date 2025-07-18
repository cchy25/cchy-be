package domain.auth.repository;

import domain.auth.entity.WhitelistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenWhitelistRepository extends JpaRepository<WhitelistItem, String> {
}
