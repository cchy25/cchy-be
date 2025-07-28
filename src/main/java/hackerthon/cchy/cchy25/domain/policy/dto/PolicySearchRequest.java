package hackerthon.cchy.cchy25.domain.policy.dto;

import hackerthon.cchy.cchy25.domain.policy.entity.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
public class PolicySearchRequest {

    private Set<RegionCode> regions; // 지역
    private Set<SupportField> supportFields; // 지원 분야
    private SupportCategory supportCategory; // 지원 종류
    private LocalDateTime applyEndAt; // 신청 마감일
    private Set<SupportTarget> supportTargets; // 지원대상
    private Set<EvaluationMethod> evaluationMethods; // 심사방법

    private String query;
}
