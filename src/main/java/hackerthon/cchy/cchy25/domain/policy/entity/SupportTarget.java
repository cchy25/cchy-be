package hackerthon.cchy.cchy25.domain.policy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum SupportTarget {
    SOLO("1인창업"),
    PRE("예비창업자"),
    RE("재창업"),
    WOMEN("여성기업"),
    UNIV("대학생"),
    SOC("사회적기업"),
    SMBIZ("소상공인"),
    ETC("기타");

    private String name;

    public static SupportTarget fromString(String s) {
        return Arrays.stream(values())
                .filter(rc -> rc.getName().equalsIgnoreCase(s.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid SupportTarget: " + s));
    }
}
