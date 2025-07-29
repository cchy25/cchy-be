package hackerthon.cchy.cchy25.domain.diagnosis.service;

import hackerthon.cchy.cchy25.domain.diagnosis.dto.DiagnosisRequest;
import hackerthon.cchy.cchy25.domain.diagnosis.dto.DiagnosisResponse;
import hackerthon.cchy.cchy25.domain.diagnosis.entity.Diagnosis;
import hackerthon.cchy.cchy25.domain.diagnosis.exception.DiagnosisErrorCode;
import hackerthon.cchy.cchy25.domain.diagnosis.exception.DiagnosisException;
import hackerthon.cchy.cchy25.domain.diagnosis.repository.DiagnosisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;

//    @Transactional(readOnly = true)
    public DiagnosisResponse getMyDiagnosis(Long userId) {
//        var foundDiagnosis = diagnosisRepository.findByUserIdRecentOne(userId)
        var foundDiagnosis = diagnosisRepository.findTop1ByUserIdOrderByCreateAtDesc(userId)
                .orElseThrow(() -> new DiagnosisException(DiagnosisErrorCode.NOT_FOUND));
        return DiagnosisResponse.from(foundDiagnosis);
    }

    @Transactional
    public Object createDiagnosis(DiagnosisRequest diagnosisRequest) {

        var newDiagnosis = Diagnosis.builder()
                .hasItem(Boolean.TRUE.equals(diagnosisRequest.getHasItem()))
                .hasTeam(Boolean.TRUE.equals(diagnosisRequest.getHasTeam()))
                .hasMentor(Boolean.TRUE.equals(diagnosisRequest.getHasMentor()))
                .hasModel(Boolean.TRUE.equals(diagnosisRequest.getHasModel()))
                .hasCapital(Boolean.TRUE.equals(diagnosisRequest.getHasCapital()))
                .hasSpace(Boolean.TRUE.equals(diagnosisRequest.getHasSpace()))
                .launchAt(diagnosisRequest.getLaunchAt())
                .years(diagnosisRequest.getYears())
                .region(diagnosisRequest.getRegion())
                .build();

        diagnosisRepository.save(newDiagnosis);

        return null;
    }

    public Object updateDiagnosis(Long userId, DiagnosisRequest diagnosisRequest) {

        return null;
    }
}
