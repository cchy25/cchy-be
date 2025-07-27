package hackerthon.cchy.cchy25.domain.policy.service;

import hackerthon.cchy.cchy25.domain.policy.dto.PolicyResponse;
import hackerthon.cchy.cchy25.domain.policy.dto.PolicySearchRequest;
import hackerthon.cchy.cchy25.domain.policy.exception.PolicyErrorCode;
import hackerthon.cchy.cchy25.domain.policy.exception.PolicyException;
import hackerthon.cchy.cchy25.domain.policy.repository.PolicyRepository;
import hackerthon.cchy.cchy25.domain.policy.repository.PolicySearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final PolicySearchRepository policySearchRepository;


    public PolicyResponse getPolicy(Long policyId) {
        var policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new PolicyException(PolicyErrorCode.NOT_FOUND));

        return PolicyResponse.from(policy);
    }

    public Page<PolicyResponse> searchPolicies(Pageable pageable, PolicySearchRequest policySearchRequest) {


        return policySearchRepository.search(
                pageable,
                policySearchRequest
        );
    }
}
