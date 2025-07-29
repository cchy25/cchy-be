package hackerthon.cchy.cchy25.domain.diagnosis.entity;

import hackerthon.cchy.cchy25.common.entity.BaseEntity;
import hackerthon.cchy.cchy25.domain.policy.entity.RegionCode;
import hackerthon.cchy.cchy25.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "diagnoses")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Diagnosis extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Builder.Default
    private Boolean hasItem = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean hasTeam = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean hasMentor = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean hasModel = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean hasCapital = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean hasSpace = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isRegistered = false;

    @Column(nullable = false)
    private LocalDateTime launchAt;

    @Column(nullable = false)
    private Integer years;

    @Column(nullable = false)
    private RegionCode region;
}
