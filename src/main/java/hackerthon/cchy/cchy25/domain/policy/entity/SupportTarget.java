package hackerthon.cchy.cchy25.domain.policy.entity;

import hackerthon.cchy.cchy25.common.entity.BaseEntity;
import hackerthon.cchy.cchy25.common.entity.Embedding;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "support_target")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SupportTarget extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Embedding embedding;
}
