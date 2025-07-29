package hackerthon.cchy.cchy25.domain.diagnosis.dto;

import hackerthon.cchy.cchy25.domain.diagnosis.entity.Diagnosis;
import hackerthon.cchy.cchy25.domain.policy.entity.RegionCode;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class DiagnosisResponse {
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

    public static DiagnosisResponse from(Diagnosis diagnosis) {
        return DiagnosisResponse.builder()
                .hasItem(Boolean.TRUE.equals(diagnosis.getHasItem()))
                .hasTeam(Boolean.TRUE.equals(diagnosis.getHasTeam()))
                .hasMentor(Boolean.TRUE.equals(diagnosis.getHasMentor()))
                .hasModel(Boolean.TRUE.equals(diagnosis.getHasModel()))
                .hasCapital(Boolean.TRUE.equals(diagnosis.getHasCapital()))
                .hasSpace(Boolean.TRUE.equals(diagnosis.getHasSpace()))
                .launchAt(diagnosis.getLaunchAt())
                .years(diagnosis.getYears())
                .region(diagnosis.getRegion())
                .build();
    }
}
