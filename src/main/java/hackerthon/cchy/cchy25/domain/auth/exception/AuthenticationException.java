package hackerthon.cchy.cchy25.domain.auth.exception;

import hackerthon.cchy.cchy25.common.exception.BaseException;

public class AuthenticationException extends BaseException {
    private AuthenticationErrorCode authenticationErrorCode;

    public AuthenticationException(AuthenticationErrorCode authenticationErrorCode){
        super(authenticationErrorCode.getMessage(), authenticationErrorCode.getHttpStatus());
    }
}
