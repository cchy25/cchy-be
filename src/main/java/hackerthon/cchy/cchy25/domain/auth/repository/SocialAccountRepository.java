package hackerthon.cchy.cchy25.domain.auth.repository;

import hackerthon.cchy.cchy25.domain.auth.entity.SocialAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {
     Optional<SocialAccount> findBySocialId(String socialId);
}
