package hackerthon.cchy.cchy25.domain.policy.controller;

import hackerthon.cchy.cchy25.domain.policy.dto.UserPolicyRequest;
import hackerthon.cchy.cchy25.domain.policy.service.PolicyService;
import hackerthon.cchy.cchy25.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserPolicyController {

    private final PolicyService policyService;

    // 사용자 정책 지원
    @PostMapping("/user-policies/{policyId}")
    public ResponseEntity<Void> updatePolicyStatus(
            @PathVariable("policyId") Long policyId,
            @AuthenticationPrincipal User user
    ) {
        policyService.applyPolicy(user.getId(), policyId);
        return ResponseEntity.ok().build();
    }

    // 사용자 정책 수정
    @PatchMapping("/user-policies/{policyId}/status")
    public ResponseEntity<Void> updatePolicyStatus(
            @PathVariable("policyId") Long policyId,
            @AuthenticationPrincipal User user,
            @RequestBody UserPolicyRequest userPolicyRequest
    ) {
        policyService.patchStatus(user.getId(), policyId, userPolicyRequest);
        return ResponseEntity.ok().build();
    }
}
