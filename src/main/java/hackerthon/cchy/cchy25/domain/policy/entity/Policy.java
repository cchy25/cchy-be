package hackerthon.cchy.cchy25.domain.policy.entity;

import hackerthon.cchy.cchy25.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "policies")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Policy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String title;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "policy_regions", joinColumns = @JoinColumn(name = "policy_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<RegionCode> regions = new HashSet<>();

    private String summary;

    private int years; // 0년 ~ 7년, 0년은 예비창업자

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "policy_support_fields", joinColumns = @JoinColumn(name = "policy_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<SupportField> supportFields = new HashSet<>(); // 사업 분야

    @Enumerated(EnumType.STRING)
    private SupportCategory supportCategory; // 정책 종류

    private String organization;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private LocalDateTime applyStartAt;

    private LocalDateTime applyEndAt;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "policy_targets", joinColumns = @JoinColumn(name = "policy_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<SupportTarget> targets = new HashSet<>(); //타겟

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "policy_apply_methods", joinColumns = @JoinColumn(name = "policy_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<ApplyMethod> applyMethods = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "policy_evaluation_methods", joinColumns = @JoinColumn(name = "policy_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<EvaluationMethod> evaluationMethods = new HashSet<>();

    private Long minAmount;

    private Long maxAmount;

    private Long isAlways;

    private String conditionDetail; // 지원조건 상세

    private String targetDetail; // 지원대상 상세

    private String exTargetDetail; // 제외대상 디테일
}



