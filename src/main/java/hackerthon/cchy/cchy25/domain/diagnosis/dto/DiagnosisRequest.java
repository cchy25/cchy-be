package hackerthon.cchy.cchy25.domain.diagnosis.dto;

import hackerthon.cchy.cchy25.domain.policy.entity.RegionCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DiagnosisRequest {

    private Boolean hasItem;

    private Boolean hasTeam;

    private Boolean hasMentor;

    private Boolean hasModel;

    private Boolean hasCapital;

    private Boolean hasSpace;

    private Boolean isRegistered;

    private LocalDateTime launchAt;

    private Integer years;

    private RegionCode region;
}
