package hackerthon.cchy.cchy25.domain.policy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum SupportTarget {
    SOLO("1인"),
    RE("재창업"),
    WOMEN("여성"),
    UNIV("대학생"),
    SOC("사회문제해결");

    private String name;

    public static SupportTarget fromString(String s) {
        return Arrays.stream(values())
                .filter(rc -> rc.name().equalsIgnoreCase(s.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid SupportTarget: " + s));
    }
}
