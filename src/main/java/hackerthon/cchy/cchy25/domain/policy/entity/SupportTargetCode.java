package hackerthon.cchy.cchy25.domain.policy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SupportTargetCode {
    PRE("예비창업자"),
    SOLO("1인 창업자"),
    LT_ONE(""),
    LT_TWO(""),
    LT_SEVEN(""),
    RE(""),
    WOMEN(""),
    UNIV(""),
    SOC("");

    private String name;

}
