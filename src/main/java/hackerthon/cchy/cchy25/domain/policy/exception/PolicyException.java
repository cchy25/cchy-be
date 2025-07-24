package hackerthon.cchy.cchy25.domain.policy.exception;

import hackerthon.cchy.cchy25.common.exception.BaseException;

public class PolicyException extends BaseException {

    public PolicyException(PolicyErrorCode code) {
        super(code.getMessage(), code.getHttpStatus());
    }
}
