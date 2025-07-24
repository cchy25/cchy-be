package hackerthon.cchy.cchy25.domain.policy.controller;

import hackerthon.cchy.cchy25.domain.policy.dto.PolicyResponse;
import hackerthon.cchy.cchy25.domain.policy.dto.PolicySearchRequest;
import hackerthon.cchy.cchy25.domain.policy.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
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
    @GetMapping("/policies/search")
    public ResponseEntity<Page<PolicyResponse>> queryPolicies(
            Pageable pageable,
            @RequestBody PolicySearchRequest policySearchRequest
    ) {
        policyService.searchPolicies(pageable);

        return ResponseEntity.ok().build();
    }
}
