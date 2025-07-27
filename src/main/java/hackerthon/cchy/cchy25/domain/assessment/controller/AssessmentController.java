//package hackerthon.cchy.cchy25.domain.assessment.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/v1")
//@RequiredArgsConstructor
//public class AssessmentController {
//
//    private final AssesmentService assesmentService;
//
//    @PostMapping("/assessments/submit")
//    public ResponseEntity<?> submit(
//            @RequestBody AssessmentRequest assessmentRequest
//    ) {
//        var assessmentResult = assessmentService.submit();
//        return ResponseEntity.ok().build();
//    }
//}
