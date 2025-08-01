package hackerthon.cchy.cchy25.domain.policy.dto;

import hackerthon.cchy.cchy25.domain.policy.entity.PolicyStatus;
import lombok.Getter;

@Getter
public class UserPolicyRequest {

    private PolicyStatus policyStatus;
}
