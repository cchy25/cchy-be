package hackerthon.cchy.cchy25.common.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "embedding")
public class Embedding extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private float[] embedding;

}
