package hackerthon.cchy.cchy25.domain.policy.dto;

import hackerthon.cchy.cchy25.domain.diagnosis.entity.Diagnosis;
import hackerthon.cchy.cchy25.domain.policy.entity.*;
import hackerthon.cchy.cchy25.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Builder
@AllArgsConstructor
@Getter
public class PolicySearchRequest {

    private Set<RegionCode> regions; // 지역
    private Set<SupportField> supportFields; // 지원 분야
    private Set<SupportCategory> supportCategories; // 지원 종류
    private LocalDateTime applyEndAt; // 신청 마감일
    private Set<SupportTarget> supportTargets; // 지원대상
    private Set<EvaluationMethod> evaluationMethods; // 심사방법

    private String query;

    public static PolicySearchRequest from(Diagnosis diagnosis) {
        return PolicySearchRequest.builder()
                .regions(diagnosis.getRegions())
                .supportFields(diagnosis.getSupportFields())
                .supportCategories(diagnosis.getSupportCategories())
                .supportTargets(diagnosis.getTargets())
                .evaluationMethods(diagnosis.getEvaluationMethods())
//                .applyEndAt()
                .build();
    }
}
