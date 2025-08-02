package hackerthon.cchy.cchy25.domain.policy.repository;

import hackerthon.cchy.cchy25.domain.policy.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
    boolean existsBySourceId(Long sourceId);
}
