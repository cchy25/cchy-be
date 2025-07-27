package hackerthon.cchy.cchy25.domain.policy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SupportTarget {
    SOLO("1인"),
    RE("재창업"),
    WOMEN("여성"),
    UNIV("대학생"),
    SOC("사회문제해결");

    private String name;

}
