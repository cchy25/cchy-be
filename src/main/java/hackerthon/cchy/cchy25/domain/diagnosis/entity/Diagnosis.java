package hackerthon.cchy.cchy25.domain.diagnosis.entity;

import hackerthon.cchy.cchy25.common.entity.BaseEntity;
import hackerthon.cchy.cchy25.domain.diagnosis.dto.DiagnosisRequest;
import hackerthon.cchy.cchy25.domain.policy.entity.*;
import hackerthon.cchy.cchy25.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Boolean hasPlanner = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean hasEdu = false;

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

//    @Column(nullable = false

    @Column(nullable = false)
    private Integer years;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "diagnosis_regions", joinColumns = @JoinColumn(name = "diagnosis_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<RegionCode> regions = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "diagnosis_support_fields", joinColumns = @JoinColumn(name = "diagnosis_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<SupportField> supportFields = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "diagnosis_support_categories", joinColumns = @JoinColumn(name = "diagnosis_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<SupportCategory> supportCategories= new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "diagnosis_support_targets", joinColumns = @JoinColumn(name = "diagnosis_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<SupportTarget> targets = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "diagnosis_evaluation_methods", joinColumns = @JoinColumn(name = "diagnosis_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<EvaluationMethod> evaluationMethods = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "diagnosis_apply_methods", joinColumns = @JoinColumn(name = "diagnosis_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<ApplyMethod> applyMethods = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "diagnosis_support_types", joinColumns = @JoinColumn(name = "diagnosis_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<SupportType> supportTypes = new HashSet<>();

    public void updateSelective(DiagnosisRequest request) {
        if (request.getHasItem() != null) this.hasItem = request.getHasItem();
        if (request.getHasTeam() != null) this.hasTeam = request.getHasTeam();
        if (request.getHasMentor() != null) this.hasMentor = request.getHasMentor();
        if (request.getHasModel() != null) this.hasModel = request.getHasModel();
        if (request.getHasCapital() != null) this.hasCapital = request.getHasCapital();
        if (request.getHasSpace() != null) this.hasSpace = request.getHasSpace();
//        if (request.getLaunchAt() != null) this.launchAt = request.getLaunchAt();

        if (request.getYears() != null) this.years = request.getYears();
        if (request.getRegions() != null) this.regions = request.getRegions();
        if (request.getSupportFields() != null) this.supportFields = request.getSupportFields();
        if (request.getTargets() != null) this.targets = request.getTargets();
        if (request.getSupportTypes() != null) this.supportTypes = request.getSupportTypes();
        if (request.getSupportCategories() != null) this.supportCategories = request.getSupportCategories();
    }
}
