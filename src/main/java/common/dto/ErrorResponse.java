package common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private String message;
    private Object data;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
