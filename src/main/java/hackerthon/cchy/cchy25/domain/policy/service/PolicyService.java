package hackerthon.cchy.cchy25.domain.policy.service;

import hackerthon.cchy.cchy25.domain.diagnosis.repository.DiagnosisRepository;
import hackerthon.cchy.cchy25.domain.policy.dto.PolicyResponse;
import hackerthon.cchy.cchy25.domain.policy.dto.PolicySearchRequest;
import hackerthon.cchy.cchy25.domain.policy.dto.UserPolicyRequest;
import hackerthon.cchy.cchy25.domain.policy.entity.Policy;
import hackerthon.cchy.cchy25.domain.policy.entity.UserPolicy;
import hackerthon.cchy.cchy25.domain.policy.exception.PolicyErrorCode;
import hackerthon.cchy.cchy25.domain.policy.exception.PolicyException;
import hackerthon.cchy.cchy25.domain.policy.repository.PolicyRepository;
import hackerthon.cchy.cchy25.domain.policy.repository.PolicySearchRepository;
import hackerthon.cchy.cchy25.domain.policy.repository.UserPolicyRepository;
import hackerthon.cchy.cchy25.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final PolicySearchRepository policySearchRepository;
    private final UserPolicyRepository userPolicyRepository;
    private final DiagnosisRepository diagnosisRepository;


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

    // 지원
    @Transactional
    public void applyPolicy(Long userId, Long policyId) {
        userPolicyRepository.save(UserPolicy.builder()
                .user(User.builder().id(userId).build())
                .policy(Policy.builder().id(policyId).build())
                .build());

    }

    @Transactional
    public void patchStatus(Long userId, Long policyId, UserPolicyRequest userPolicyRequest) {
        var foundUserPolicy = userPolicyRepository.findByUserIdAndPolicyId(userId, policyId)
                .orElseThrow(() -> new PolicyException(PolicyErrorCode.NOT_FOUND));

        foundUserPolicy.setPolicyStatus(userPolicyRequest.getPolicyStatus());

        userPolicyRepository.save(foundUserPolicy);
    }


    public Page<PolicyResponse> recommend(Pageable pageable, Long userId) {
        var diagnosis = diagnosisRepository.findTop1ByUserIdOrderByCreateAtDesc(userId)
                .orElseThrow(() -> new PolicyException(PolicyErrorCode.NOT_FOUND));

        return policySearchRepository.search(
//                PageRequest.of(0, 3),
                pageable,
                PolicySearchRequest.from(diagnosis)
        );
    }
}
