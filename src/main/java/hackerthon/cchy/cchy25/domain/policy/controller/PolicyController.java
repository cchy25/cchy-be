package hackerthon.cchy.cchy25.domain.policy.controller;

import hackerthon.cchy.cchy25.domain.policy.dto.PolicyResponse;
import hackerthon.cchy.cchy25.domain.policy.dto.PolicySearchRequest;
import hackerthon.cchy.cchy25.domain.policy.service.PolicyService;
import hackerthon.cchy.cchy25.domain.user.entity.User;
import hackerthon.cchy.cchy25.global.scheduler.PolicyTransferScheduler;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService policyService;

    // 디테일
    @GetMapping("/policies/{policyId}")
    public ResponseEntity<PolicyResponse> getDetails(
            @PathVariable("policyId") Long policyId
    ) {
        return ResponseEntity.ok(policyService.getPolicy(policyId));
    }


    // 리스트 검색
    @PostMapping("/policies/search")
    public ResponseEntity<Page<PolicyResponse>> queryPolicies(
            Pageable pageable,
            @RequestBody PolicySearchRequest policySearchRequest
            ) {
        return ResponseEntity.ok(policyService.searchPolicies(pageable, policySearchRequest));
    }

    // 리스트 추천
    @PostMapping("/policies/recommend")
    public ResponseEntity<Page<PolicyResponse>> recommend (
            Pageable pageable,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(policyService.recommend(pageable, user.getId()));
    }

    private final PolicyTransferScheduler policyTransferScheduler;
    @PostMapping("/policies/bulk")
    public ResponseEntity<Void> bulk(){
        policyTransferScheduler.runJob();
        return ResponseEntity.ok().build();
    }
}
