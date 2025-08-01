package hackerthon.cchy.cchy25.domain.diagnosis.service;

import hackerthon.cchy.cchy25.domain.diagnosis.dto.DiagnosisRequest;
import hackerthon.cchy.cchy25.domain.diagnosis.dto.DiagnosisResponse;
import hackerthon.cchy.cchy25.domain.diagnosis.entity.Diagnosis;
import hackerthon.cchy.cchy25.domain.diagnosis.exception.DiagnosisErrorCode;
import hackerthon.cchy.cchy25.domain.diagnosis.exception.DiagnosisException;
import hackerthon.cchy.cchy25.domain.diagnosis.repository.DiagnosisRepository;
import hackerthon.cchy.cchy25.domain.user.entity.User;
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
    public Object createDiagnosis(Long userId, DiagnosisRequest diagnosisRequest) {

        var newDiagnosis = Diagnosis.builder()
                .user(User.builder().id(userId).build())
                .hasItem(Boolean.TRUE.equals(diagnosisRequest.getHasItem()))
                .hasTeam(Boolean.TRUE.equals(diagnosisRequest.getHasTeam()))
                .hasMentor(Boolean.TRUE.equals(diagnosisRequest.getHasMentor()))
                .hasModel(Boolean.TRUE.equals(diagnosisRequest.getHasModel()))
                .hasCapital(Boolean.TRUE.equals(diagnosisRequest.getHasCapital()))
                .hasSpace(Boolean.TRUE.equals(diagnosisRequest.getHasSpace()))
                .launchAt(diagnosisRequest.getLaunchAt())
                .years(diagnosisRequest.getYears())
                .regions(diagnosisRequest.getRegions())
                .supportFields(diagnosisRequest.getSupportFields())
                .targets(diagnosisRequest.getTargets())
                .build();

        diagnosisRepository.save(newDiagnosis);

        return null;
    }

    public Object updateDiagnosis(Long userId, DiagnosisRequest diagnosisRequest) {
        var foundDiagnosis = diagnosisRepository.findTop1ByUserIdOrderByCreateAtDesc(userId)
                .orElseThrow(() -> new DiagnosisException(DiagnosisErrorCode.NOT_FOUND));

        if(diagnosisRequest.getRegions().isEmpty()) {
            throw new DiagnosisException(DiagnosisErrorCode.EMPTY_LIST);
        }

        if(diagnosisRequest.getTargets().isEmpty()) {
            throw new DiagnosisException(DiagnosisErrorCode.EMPTY_LIST);
        }

        if(diagnosisRequest.getSupportFields().isEmpty()) {
            throw new DiagnosisException(DiagnosisErrorCode.EMPTY_LIST);
        }

        foundDiagnosis.updateSelective(diagnosisRequest);
        return null;
    }
}