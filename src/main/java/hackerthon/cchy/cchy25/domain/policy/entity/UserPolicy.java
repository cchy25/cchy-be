package hackerthon.cchy.cchy25.domain.policy.entity;

import hackerthon.cchy.cchy25.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_policies")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Policy policy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    @Setter
    private PolicyStatus policyStatus = PolicyStatus.APPLIED;
}
