package hackerthon.cchy.cchy25.domain.policy.dto;

import hackerthon.cchy.cchy25.domain.diagnosis.entity.Diagnosis;
import hackerthon.cchy.cchy25.domain.policy.entity.*;
import hackerthon.cchy.cchy25.domain.user.entity.User;
import jakarta.persistence.Column;
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
    private Boolean hasItem;
    private Boolean hasTeam;
    private Boolean hasMentor;
    private Boolean hasModel;
    private Boolean hasCapital;
    private Boolean hasSpace;
    private Boolean isRegistered;
    private Boolean hasEdu;
    private Boolean hasPlanner;
    private Set<RegionCode> regions; // 지역
    private Set<SupportField> supportFields; // 지원 분야
    private Set<SupportCategory> supportCategories; // 지원 종류
    private LocalDateTime applyEndAt; // 신청 마감일
    private Set<SupportTarget> supportTargets; // 지원대상
    private Set<SupportType> supportTypes; // 지원종류
    private Set<EvaluationMethod> evaluationMethods; // 심사방법

    private String query;

    public static PolicySearchRequest from(Diagnosis diagnosis) {
        return PolicySearchRequest.builder()
                .hasMentor(diagnosis.getHasMentor())
                .hasModel(diagnosis.getHasModel())
                .hasCapital(diagnosis.getHasCapital())
                .hasSpace(diagnosis.getHasSpace())
                .isRegistered(diagnosis.getIsRegistered())
                .hasEdu(diagnosis.getHasEdu())
                .hasTeam(diagnosis.getHasTeam())
                .hasItem(diagnosis.getHasItem())
                .hasPlanner(diagnosis.getHasPlanner())
                .regions(diagnosis.getRegions())
                .supportFields(diagnosis.getSupportFields())
                .supportCategories(diagnosis.getSupportCategories())
                .supportTargets(diagnosis.getTargets())
                .supportTypes(diagnosis.getSupportTypes())
                .evaluationMethods(diagnosis.getEvaluationMethods())
//                .applyEndAt()
                .build();
    }
}
