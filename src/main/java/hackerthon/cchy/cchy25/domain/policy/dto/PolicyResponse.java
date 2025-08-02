package hackerthon.cchy.cchy25.domain.policy.dto;

import hackerthon.cchy.cchy25.domain.policy.entity.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
public class PolicyResponse {

    private final String title;
    private final String summary;
    private final String url;
    private final String targetDetail;
    private final String exTargetDetail;
    private final String organization;
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;
    private final LocalDateTime applyStartAt;
    private final LocalDateTime applyEndAt;
    private final Long minAmount;
    private final Long maxAmount;
    private final int years;
    private final Set<RegionCode> regionCodes;
    private final Set<EvaluationMethod> evaluationMethods;
    private final Set<SupportCategory> supportCategories;
    private final Set<SupportField> supportFields;
    private final Set<SupportType> supportTypes;
    private final Set<SupportTarget> supportTargets;
    private final Set<ApplyMethod> applyMethods;

    private final Integer accuracy;
    private final Boolean bookmarked;
//    private final PolicyStatus policyStatus;

    public static PolicyResponse from(Policy policy, boolean bookmarked) {
        return PolicyResponse.builder()
                .title(policy.getTitle())
                .summary(policy.getSummary())
                .url(policy.getUrl())
                .targetDetail(policy.getTargetDetail())
                .exTargetDetail(policy.getExTargetDetail())
                .organization(policy.getOrganization())
                .startAt(policy.getStartAt())
                .endAt(policy.getEndAt())
                .applyStartAt(policy.getApplyStartAt())
                .applyEndAt(policy.getApplyEndAt())
                .minAmount(policy.getMinAmount())
                .maxAmount(policy.getMaxAmount())
                .years(policy.getYears())
                .regionCodes(policy.getRegions())
                .evaluationMethods(policy.getEvaluationMethods())
                .supportCategories(policy.getSupportCategories())
                .supportFields(policy.getSupportFields())
                .supportTypes(policy.getSupportTypes())
                .supportTargets(policy.getTargets())
                .applyMethods(policy.getApplyMethods())
                .bookmarked(bookmarked) // 북마크 목록은 accuracy 필요없음
//                .accuracy(accuracy) // 북마크 목록은 accuracy 필요없음
                .build();
    }

    public static PolicyResponse from(Policy policy, int accuracy, boolean bookmarked) {
        return PolicyResponse.builder()
                .title(policy.getTitle())
                .summary(policy.getSummary())
                .url(policy.getUrl())
                .targetDetail(policy.getTargetDetail())
                .exTargetDetail(policy.getExTargetDetail())
                .organization(policy.getOrganization())
                .startAt(policy.getStartAt())
                .endAt(policy.getEndAt())
                .applyStartAt(policy.getApplyStartAt())
                .applyEndAt(policy.getApplyEndAt())
                .minAmount(policy.getMinAmount())
                .maxAmount(policy.getMaxAmount())
                .years(policy.getYears())
                .regionCodes(policy.getRegions())
                .evaluationMethods(policy.getEvaluationMethods())
                .supportCategories(policy.getSupportCategories())
                .supportFields(policy.getSupportFields())
                .supportTypes(policy.getSupportTypes())
                .supportTargets(policy.getTargets())
                .applyMethods(policy.getApplyMethods())
                .bookmarked(bookmarked) // 북마크 목록은 accuracy 필요없음
                .accuracy(accuracy) // 북마크 목록은 accuracy 필요없음
                .build();
    }
}
