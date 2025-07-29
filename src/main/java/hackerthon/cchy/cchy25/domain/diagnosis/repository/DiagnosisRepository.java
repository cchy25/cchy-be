package hackerthon.cchy.cchy25.domain.diagnosis.repository;

import hackerthon.cchy.cchy25.domain.diagnosis.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

//    @Query("""
//        SELECT d
//        FROM Diagnosis d
//        WHERE d.user.id = :userId
//        ORDER BY d.createAt DESC
//        LIMIT 1
//        """)
//    Optional<Diagnosis> findByUserIdRecentOne(@Param("userId") Long userId);
    Optional<Diagnosis> findTop1ByUserIdOrderByCreateAtDesc(Long userId);

}
