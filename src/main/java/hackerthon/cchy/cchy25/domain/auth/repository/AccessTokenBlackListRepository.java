package hackerthon.cchy.cchy25.domain.auth.repository;

import hackerthon.cchy.cchy25.domain.auth.entity.BlacklistItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessTokenBlackListRepository extends CrudRepository<BlacklistItem, String> {
}
