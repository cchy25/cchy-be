package hackerthon.cchy.cchy25.domain.policy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BusinessType {

    CULTURE("문화예술"),
    CONTENT("콘텐츠"),
    TECH("기술개발"),
    MANUFACTURE("제조업"),
    AGRICULTURE("농업"),
    FB("요식업"),
    SOCIAL("사회문제해결"),
    ETC("기타");

    private String name;
}
