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

    @Enumerated(EnumType.STRING)
    private RegionCode region;

    private String summary;

    private int years; // 0년 ~ 7년, 0년은 예비창업자

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<BusinessType> bizType; // 사업 분야

    private SupportType supportType; // 정책 종류

    private String organization;

    private LocalDateTime bizStartAt;

    private LocalDateTime bizEndAt;

    private LocalDateTime applyStartAt;

    private LocalDateTime applyEndAt;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<SupportTarget> targets = new HashSet<>(); //타겟

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<ApplyMethod> applyMethods = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<EvaluationMethod> evaluationMethods = new HashSet<>();

    private Long minAmount;

    private Long maxAmount;

    private Long isAlways;

    private String condition; // 지원조건 상세

    private String targetDetail; // 지원대상 상세

    private String exTargetDetail; // 제외대상 디테일
}



