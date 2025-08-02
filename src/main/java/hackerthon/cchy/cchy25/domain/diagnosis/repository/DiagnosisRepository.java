package hackerthon.cchy.cchy25.domain.diagnosis.repository;

import hackerthon.cchy.cchy25.domain.diagnosis.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

    Optional<Diagnosis> findFirstByUser_IdOrderByCreateAtDesc(Long userId);

}
