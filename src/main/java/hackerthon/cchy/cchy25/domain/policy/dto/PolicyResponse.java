package hackerthon.cchy.cchy25.domain.policy.dto;

import com.querydsl.core.annotations.QueryProjection;
import hackerthon.cchy.cchy25.domain.policy.entity.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.AnyKeyJavaClass;

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

    @Builder
    @QueryProjection
    public PolicyResponse(String title, String summary, String url, String targetDetail, String exTargetDetail, String organization, LocalDateTime startAt, LocalDateTime endAt, LocalDateTime applyStartAt, LocalDateTime applyEndAt, Long minAmount, Long maxAmount, int years, Set<RegionCode> regionCodes, Set<EvaluationMethod> evaluationMethods, Set<SupportCategory> supportCategories, Set<SupportField> supportFields, Set<SupportType> supportTypes, Set<SupportTarget> supportTargets, Set<ApplyMethod> applyMethods, Integer accuracy, Boolean bookmarked){//, PolicyStatus policyStatus) {
        this.title = title;
        this.summary = summary;
        this.url = url;
        this.targetDetail = targetDetail;
        this.exTargetDetail = exTargetDetail;
        this.organization = organization;
        this.startAt = startAt;
        this.endAt = endAt;
        this.applyStartAt = applyStartAt;
        this.applyEndAt = applyEndAt;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.years = years;
        this.regionCodes = regionCodes;
        this.evaluationMethods = evaluationMethods;
        this.supportCategories = supportCategories;
        this.supportFields = supportFields;
        this.supportTypes = supportTypes;
        this.supportTargets = supportTargets;
        this.applyMethods = applyMethods;
        this.accuracy = accuracy;
        this.bookmarked = bookmarked != null && bookmarked;
//        this.policyStatus = policyStatus;
    }

    public static PolicyResponse from(Policy policy, boolean b) {
        return PolicyResponse.builder()
                .title(policy.getTitle())
                .summary(policy.getSummary())
                .url(policy.getUrl())
                .bookmarked(b) // 북마크 목록은 accuracy 필요없음
                .build();
    }
}
