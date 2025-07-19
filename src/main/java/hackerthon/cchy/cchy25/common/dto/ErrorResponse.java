package hackerthon.cchy.cchy25.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private Object data;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
