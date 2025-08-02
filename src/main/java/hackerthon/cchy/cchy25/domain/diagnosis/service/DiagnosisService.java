package hackerthon.cchy.cchy25.domain.diagnosis.service;

import hackerthon.cchy.cchy25.domain.diagnosis.dto.DiagnosisRequest;
import hackerthon.cchy.cchy25.domain.diagnosis.dto.DiagnosisResponse;
import hackerthon.cchy.cchy25.domain.diagnosis.entity.Diagnosis;
import hackerthon.cchy.cchy25.domain.diagnosis.exception.DiagnosisErrorCode;
import hackerthon.cchy.cchy25.domain.diagnosis.exception.DiagnosisException;
import hackerthon.cchy.cchy25.domain.diagnosis.repository.DiagnosisRepository;
import hackerthon.cchy.cchy25.domain.policy.entity.EvaluationMethod;
import hackerthon.cchy.cchy25.domain.policy.entity.SupportCategory;
import hackerthon.cchy.cchy25.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;

//    @Transactional(readOnly = true)
    public DiagnosisResponse getMyDiagnosis(Long userId) {
//        var foundDiagnosis = diagnosisRepository.findByUserIdRecentOne(userId)
        var foundDiagnosis = diagnosisRepository.findFirstByUser_IdOrderByCreateAtDesc(userId)
                .orElseThrow(() -> new DiagnosisException(DiagnosisErrorCode.NOT_FOUND));
        return DiagnosisResponse.from(foundDiagnosis);
    }

    @Transactional
    public Object createDiagnosis(Long userId, DiagnosisRequest diagnosisRequest) {
        Set<SupportCategory> supportCategories = new HashSet<>(
                Optional.ofNullable(diagnosisRequest.getSupportCategories()).orElse(Collections.emptySet())
        );

        // hasCapital, hasSpace 등의 조건에 따라 새로운 Set에 항목 추가
        if (Boolean.FALSE.equals(diagnosisRequest.getHasCapital())) {
            supportCategories.add(SupportCategory.FUNDING);
        }
        if (Boolean.FALSE.equals(diagnosisRequest.getHasSpace())) {
            supportCategories.add(SupportCategory.SPACE);
        }
        if (Boolean.FALSE.equals(diagnosisRequest.getHasEdu())) {
            supportCategories.add(SupportCategory.TUTOR);
        }
        if (Boolean.FALSE.equals(diagnosisRequest.getHasTeam())) {
            supportCategories.add(SupportCategory.TEAM);
        }
        if (Boolean.FALSE.equals(diagnosisRequest.getHasMentor())) {
            supportCategories.add(SupportCategory.MENTORING);
        }

        // 빌더에 새롭게 생성된 supportCategories를 전달
        var newDiagnosis = Diagnosis.builder()
                .user(User.builder().id(userId).build())
                .hasItem(Boolean.TRUE.equals(diagnosisRequest.getHasItem()))
                .hasTeam(Boolean.TRUE.equals(diagnosisRequest.getHasTeam()))
                .hasMentor(Boolean.TRUE.equals(diagnosisRequest.getHasMentor()))
                .hasModel(Boolean.TRUE.equals(diagnosisRequest.getHasModel()))
                .hasCapital(Boolean.TRUE.equals(diagnosisRequest.getHasCapital()))
                .hasSpace(Boolean.TRUE.equals(diagnosisRequest.getHasSpace()))
                .years(diagnosisRequest.getYears())
                .regions(diagnosisRequest.getRegions())
                .evaluationMethods(Boolean.FALSE.equals(diagnosisRequest.getHasPlanner()) ? Set.of(EvaluationMethod.PLANNER) : new HashSet<>())
                .supportFields(diagnosisRequest.getSupportFields())
                .supportCategories(supportCategories) // 새로 만든 Set을 사용
                .targets(diagnosisRequest.getTargets())
                .build();

        diagnosisRepository.save(newDiagnosis);

        return null;
    }

    public Object updateDiagnosis(Long userId, DiagnosisRequest diagnosisRequest) {
        var foundDiagnosis = diagnosisRepository.findFirstByUser_IdOrderByCreateAtDesc(userId)
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