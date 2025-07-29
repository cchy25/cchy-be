package hackerthon.cchy.cchy25.domain.diagnosis.controller;

import hackerthon.cchy.cchy25.domain.diagnosis.dto.DiagnosisRequest;
import hackerthon.cchy.cchy25.domain.diagnosis.dto.DiagnosisResponse;
import hackerthon.cchy.cchy25.domain.diagnosis.service.DiagnosisService;
import hackerthon.cchy.cchy25.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DiagnosisController {

    private final DiagnosisService diagnosisService;

    // 내 상태 확인
    @GetMapping("/diagnoses/me")
    public ResponseEntity<DiagnosisResponse> createDiagnosis(
            @AuthenticationPrincipal User user,
            @RequestBody DiagnosisRequest diagnosisRequest
    ) {
        return ResponseEntity.ok(diagnosisService.getMyDiagnosis(user.getId()));
    }

    // 상태(진단서) 제출
    @PostMapping("/diagnoses")
    public ResponseEntity<Void> createDiagnosis(
            @RequestBody DiagnosisRequest diagnosisRequest
    ) {
        diagnosisService.createDiagnosis(diagnosisRequest);
        return ResponseEntity.ok().build();
    }

    // 상태 수정
    @PutMapping("/diagnoses/me")
    public ResponseEntity<Void> updateDiagnosis(
            @AuthenticationPrincipal User user,
            @RequestBody DiagnosisRequest diagnosisRequest
    ) {
        diagnosisService.updateDiagnosis(user.getId(), diagnosisRequest);
        return ResponseEntity.ok().build();
    }
}
