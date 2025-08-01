package hackerthon.cchy.cchy25.domain.policy.dto;

import hackerthon.cchy.cchy25.domain.policy.entity.Policy;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PolicyResponse {

    private String title;
    private String summary;
    private String url;

    public static PolicyResponse from(Policy policy) {
        return PolicyResponse.builder()
                .title(policy.getTitle())
                .url(policy.getUrl())
                .summary(policy.getSummary())
                .build();
    }
}
