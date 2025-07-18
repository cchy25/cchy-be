package domain.auth.exception;

import common.exception.BaseException;

public class AuthenticationException extends BaseException {
    private AuthenticationErrorCode authenticationErrorCode;

    public AuthenticationException(AuthenticationErrorCode authenticationErrorCode){
        super(authenticationErrorCode.getMessage(), authenticationErrorCode.getHttpStatus());
    }
}
