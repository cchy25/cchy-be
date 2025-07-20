package hackerthon.cchy.cchy25.domain.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthenticationErrorCode {
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "잘못된 이메일 또는 비밀번호입니다."),
    CONFIRM_PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "확인비밀번호가 일치하지 않습니다."),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "중복된 유저명입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다.");

    private HttpStatus httpStatus;
    private String message;
}
