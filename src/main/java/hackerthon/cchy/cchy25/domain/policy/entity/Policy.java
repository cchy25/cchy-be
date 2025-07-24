package hackerthon.cchy.cchy25.domain.policy.entity;

import hackerthon.cchy.cchy25.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
