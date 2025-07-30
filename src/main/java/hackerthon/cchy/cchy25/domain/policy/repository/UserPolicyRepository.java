package hackerthon.cchy.cchy25.domain.policy.repository;

import hackerthon.cchy.cchy25.domain.policy.entity.UserPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPolicyRepository extends JpaRepository<UserPolicy, Long> {


    Page<UserPolicy> findByUserId(Long userId, Pageable pageable);

    Optional<UserPolicy> findByUserIdAndPolicyId(Long userId, Long policyId);
}
