package hackerthon.cchy.cchy25.domain.policy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SupportCategory {
    CONTEST("공모전/경진대회"),
    SUPPORT("창업지원"),
    ETC("기타");

    private String name;
}
