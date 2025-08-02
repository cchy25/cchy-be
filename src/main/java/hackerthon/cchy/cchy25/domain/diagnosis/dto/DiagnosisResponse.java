package hackerthon.cchy.cchy25.domain.diagnosis.dto;

import hackerthon.cchy.cchy25.domain.diagnosis.entity.Diagnosis;
import hackerthon.cchy.cchy25.domain.policy.entity.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
public class DiagnosisResponse {
    private Boolean hasItem;

    private Boolean hasPlanner;

    private Boolean hasEdu;

    private Boolean hasTeam;

    private Boolean hasMentor;

    private Boolean hasModel;

    private Boolean hasCapital;

    private Boolean hasSpace;

    private Boolean isRegistered;

//    private LocalDateTime launchAt;

    private Integer years;

    private Set<RegionCode> regions;

    private Set<SupportField> supportFields;

    private Set<SupportCategory> supportCategories;

    private Set<EvaluationMethod> evaluationMethods;

    private Set<ApplyMethod> applyMethods;

    private Set<SupportType> supportTypes;

    private Set<SupportTarget> targets;



    public static DiagnosisResponse from(Diagnosis diagnosis) {
        return DiagnosisResponse.builder()
                .hasItem(Boolean.TRUE.equals(diagnosis.getHasItem()))
                .hasEdu(Boolean.TRUE.equals(diagnosis.getHasEdu()))
                .hasPlanner(Boolean.TRUE.equals(diagnosis.getHasPlanner()))
                .hasTeam(Boolean.TRUE.equals(diagnosis.getHasTeam()))
                .hasMentor(Boolean.TRUE.equals(diagnosis.getHasMentor()))
                .hasModel(Boolean.TRUE.equals(diagnosis.getHasModel()))
                .hasCapital(Boolean.TRUE.equals(diagnosis.getHasCapital()))
                .hasSpace(Boolean.TRUE.equals(diagnosis.getHasSpace()))
//                .launchAt(diagnosis.getLaunchAt())
                .years(diagnosis.getYears())
                .regions(diagnosis.getRegions())
                .supportFields(diagnosis.getSupportFields())
                .supportCategories(diagnosis.getSupportCategories())
                .evaluationMethods(diagnosis.getEvaluationMethods())
                .supportTypes(diagnosis.getSupportTypes())
                .applyMethods(diagnosis.getApplyMethods())
                .targets(diagnosis.getTargets())
                .build();
    }
}
