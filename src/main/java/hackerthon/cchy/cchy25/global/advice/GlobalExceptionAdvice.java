package hackerthon.cchy.cchy25.global.advice;

import hackerthon.cchy.cchy25.common.dto.ErrorResponse;
import hackerthon.cchy.cchy25.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> AuthenticationExceptionHandler(BaseException ex) {
        log.warn("{}:{}", ex.getClass().getSimpleName(), ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(new ErrorResponse(ex.getMessage()));
    }
}
