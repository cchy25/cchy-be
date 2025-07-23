package hackerthon.cchy.cchy25.domain.user.exception;

import hackerthon.cchy.cchy25.common.exception.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserException extends BaseException {

    public UserException(UserErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode.getHttpStatus());
    }
}
