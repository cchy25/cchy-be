package hackerthon.cchy.cchy25.domain.policy.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "source")
@Getter
@NoArgsConstructor
public class SourcePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String title;

    private String regions;

    private String summary;

    @Column(columnDefinition = "int default 0")
    private Integer years; // 0년 ~ 7년, 0년은 예비창업자

    private String supportFields; // 사업 분야

//    @Enumerated(EnumType.STRING)
//    private SupportCategory supportCategory; // 정책 종류
    private String supportCategory; // 정책 종류

    private String organization;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private LocalDateTime applyStartAt;

    private LocalDateTime applyEndAt;

    private String targets; //타겟

    private String applyMethods;

    private String evaluationMethods;

    private Long minAmount;

    private Long maxAmount;

    private String conditionDetail; // 지원조건 상세

    private String targetDetail; // 지원대상 상세

    private String exTargetDetail; // 제외대상 디테일
}
