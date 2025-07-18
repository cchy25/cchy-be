package domain.auth.dto;

import lombok.Builder;

@Builder
public record PasswordResetRequest (
        String verificationCode,
        String password,
        String newPassword,
        String newConfirmPassword
) { }
