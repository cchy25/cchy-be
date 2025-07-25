package hackerthon.cchy.cchy25.domain.policy.repository;

import hackerthon.cchy.cchy25.domain.policy.dto.PolicyResponse;
import hackerthon.cchy.cchy25.domain.policy.dto.PolicySearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class PolicySearchRepositoryImpl implements PolicySearchRepository{

    @Override
    public Page<PolicyResponse> search(Pageable pageable, PolicySearchRequest policySearchRequest) {
        return null;
    }
}
