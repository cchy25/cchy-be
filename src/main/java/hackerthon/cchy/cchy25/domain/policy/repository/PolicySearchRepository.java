package hackerthon.cchy.cchy25.domain.policy.repository;

import hackerthon.cchy.cchy25.domain.policy.dto.PolicyResponse;
import hackerthon.cchy.cchy25.domain.policy.dto.PolicySearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PolicySearchRepository {

    Page<PolicyResponse> search(Pageable pageable, PolicySearchRequest policySearchRequest);
}
