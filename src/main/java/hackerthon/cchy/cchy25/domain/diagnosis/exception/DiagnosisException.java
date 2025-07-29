package hackerthon.cchy.cchy25.domain.diagnosis.exception;

import hackerthon.cchy.cchy25.common.exception.BaseException;
import lombok.Getter;

@Getter
public class DiagnosisException extends BaseException {

    public DiagnosisException(DiagnosisErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode.getHttpStatus());
    }
}
