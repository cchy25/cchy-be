package hackerthon.cchy.cchy25.domain.diagnosis.dto;

import hackerthon.cchy.cchy25.domain.policy.entity.RegionCode;
import hackerthon.cchy.cchy25.domain.policy.entity.SupportCategory;
import hackerthon.cchy.cchy25.domain.policy.entity.SupportField;
import hackerthon.cchy.cchy25.domain.policy.entity.SupportTarget;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
public class DiagnosisRequest {

    private Boolean hasItem;

    private Boolean hasPlanner;

    private Boolean hasEdu;

    private Boolean hasTeam;

    private Boolean hasMentor;

    private Boolean hasModel;

    private Boolean hasCapital;

    private Boolean hasSpace;

    private Boolean isRegistered;

    private LocalDateTime launchAt;

    private Integer years;

    private Set<RegionCode> regions;

    private Set<SupportField> supportFields;

    private Set<SupportCategory> supportCategories;

    private Set<SupportTarget> targets;
}
