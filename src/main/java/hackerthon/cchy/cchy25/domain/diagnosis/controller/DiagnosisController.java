package hackerthon.cchy.cchy25.domain.diagnosis.controller;

import hackerthon.cchy.cchy25.domain.diagnosis.dto.DiagnosisRequest;
import hackerthon.cchy.cchy25.domain.diagnosis.dto.DiagnosisResponse;
import hackerthon.cchy.cchy25.domain.diagnosis.service.DiagnosisService;
import hackerthon.cchy.cchy25.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Diagnosis", description = "진단 API")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class DiagnosisController {

    private final DiagnosisService diagnosisService;

    // 내 상태 확인
    @Operation(summary = "내 진단 조회", description = "유저의 진단 정보를 조회합니다.")
    @GetMapping("/diagnoses/me")
    public ResponseEntity<DiagnosisResponse> getMyDiagnosis(
            @AuthenticationPrincipal User user,
            @RequestBody DiagnosisRequest diagnosisRequest
    ) {
        return ResponseEntity.ok(diagnosisService.getMyDiagnosis(user.getId()));
    }

    // 상태(진단서) 제출
    @Operation(summary = "진단 제출", description = "유저의 진단 정보를 생성합니다.")
    @PostMapping("/diagnoses")
    public ResponseEntity<Void> createDiagnosis(
            @AuthenticationPrincipal User user,
            @RequestBody DiagnosisRequest diagnosisRequest
    ) {
        diagnosisService.createDiagnosis(user.getId(), diagnosisRequest);
        return ResponseEntity.ok().build();
    }

    // 상태 수정
    @Operation(summary = "내 진단 수정", description = "유저의 진단 정보를 수정합니다.")
    @PutMapping("/diagnoses/me")
    public ResponseEntity<Void> updateDiagnosis(
            @AuthenticationPrincipal User user,
            @RequestBody DiagnosisRequest diagnosisRequest
    ) {
        diagnosisService.updateDiagnosis(user.getId(), diagnosisRequest);
        return ResponseEntity.ok().build();
    }
}
