package hackerthon.cchy.cchy25.domain.policy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PolicyErrorCode {

    NOT_FOUND("정책을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private String message;
    private HttpStatus httpStatus;
}
