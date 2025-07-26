package hackerthon.cchy.cchy25.domain.policy.entity;

import hackerthon.cchy.cchy25.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    private String summary;

//    private BusinessType businessType; // 사업 분야

//    private SupportType supportType; // 정책 종류

    private String organization;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

//    @Enumerated
//    private List<SupportTarget> targets;

//    @Enumerated
//    private List<EvaluationMethod> evaluationMethods;

//    private ApplicationMethod applicationMethod;

//    private String currency;

    private Long minAmount;

    private Long maxAmount;

    private Long isAlways;

    private String condition;

//    private List<PolicyTag> tags = new ArrayList();




}
