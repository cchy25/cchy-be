package hackerthon.cchy.cchy25.domain.auth.entity;

import hackerthon.cchy.cchy25.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "social_accounts")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SocialAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String socialId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
