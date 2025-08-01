package hackerthon.cchy.cchy25.domain.diagnosis.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum DiagnosisErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "진단 기록이 없습니다."),
    EMPTY_LIST(HttpStatus.BAD_REQUEST, "빈 항목이 존재합니다.");

    private HttpStatus httpStatus;
    private String message;
}
