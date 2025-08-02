package hackerthon.cchy.cchy25.domain.policy.dto;

import com.querydsl.core.annotations.QueryProjection;
import hackerthon.cchy.cchy25.domain.policy.entity.Policy;
import hackerthon.cchy.cchy25.domain.policy.entity.PolicyStatus;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.AnyKeyJavaClass;

@Getter
@Builder
public class PolicyResponse {

    private final String title;
    private final String summary;
    private final String url;
    private final Integer accuracy;
    private final Boolean bookmarked;
//    private final PolicyStatus policyStatus;

    @Builder
    @QueryProjection
    public PolicyResponse(String title, String summary, String url, Integer accuracy, Boolean bookmarked){//, PolicyStatus policyStatus) {
        this.title = title;
        this.summary = summary;
        this.url = url;
        this.accuracy = accuracy;
        this.bookmarked = bookmarked != null && bookmarked;
//        this.policyStatus = policyStatus;
    }

    public static PolicyResponse from(Policy policy, boolean b) {
        return PolicyResponse.builder()
                .title(policy.getTitle())
                .summary(policy.getSummary())
                .url(policy.getUrl())
                .bookmarked(b) // 북마크 목록은 accuracy 필요없음
                .build();
    }
}
