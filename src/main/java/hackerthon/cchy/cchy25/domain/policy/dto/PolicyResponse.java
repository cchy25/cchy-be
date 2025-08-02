package hackerthon.cchy.cchy25.domain.policy.dto;

import hackerthon.cchy.cchy25.domain.policy.entity.Policy;
import hackerthon.cchy.cchy25.domain.policy.entity.PolicyStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PolicyResponse {

    private String title;
    private String summary;
    private String url;
    private Integer accuracy;
    private Boolean bookmarked; //Bookmark 엔티티()
    private PolicyStatus policyStatus; // PolicyStatus 열거형

    public static PolicyResponse from(Policy policy) {
        return PolicyResponse.builder()
                .title(policy.getTitle())
                .url(policy.getUrl())
                .summary(policy.getSummary())
                .build();
    }

    public static PolicyResponse from(Policy policy, PolicySearchRequest policySearchRequest) {
        return PolicyResponse.builder()
                .title(policy.getTitle())
                .url(policy.getUrl())
                .summary(policy.getSummary())
                .accuracy(calcAccuracy(policy, policySearchRequest))
                .build();
    }

    private static Integer calcAccuracy(Policy policy, PolicySearchRequest policySearchRequest) {
        // avg((정책의 지역이고 요청에 존재하는 수 / 요청의 지역 수) + (정책의 타겟이고 요청에 존재하는 수 / 요청의 타겟 수) ... )
        double regionAccuracy = (double) (policy.getRegions().stream()
                .filter(s->policySearchRequest.getRegions().contains(s))
                .count()) / (policySearchRequest.getRegions().stream().count());

        double fieldAccuracy = (double) (policy.getSupportFields().stream()
                .filter(s->policySearchRequest.getSupportFields().contains(s))
                .count()) / (policySearchRequest.getSupportFields().stream().count());

        double targetAccuracy = (double) (policy.getTargets().stream()
                .filter(s->policySearchRequest.getSupportTargets().contains(s))
                .count()) / (policySearchRequest.getSupportTargets().stream().count());

        double categoryAccuracy = (double) (policy.getSupportCategories().stream()
                .filter(s->policySearchRequest.getSupportCategories().contains(s))
                .count()) / (policySearchRequest.getSupportCategories().stream().count());

        return (int) Math.round(((regionAccuracy + fieldAccuracy + targetAccuracy + categoryAccuracy) / 4.0) * 100);
    }
}
