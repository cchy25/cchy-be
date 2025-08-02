package hackerthon.cchy.cchy25.domain.policy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum EvaluationMethod {
    APPLY("신청"),
    PLANNER("사업 계획서 제출"),
    INTERVIEW("인터뷰"),
    PRESENTATION("발표"),
    ONSITE("현장심사"),
    ETC("기타");

    private String name;

    public static EvaluationMethod fromString(String s) {
        return Arrays.stream(values())
                .filter(rc -> rc.name().equalsIgnoreCase(s.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid EvaluationMethod: " + s));
    }
}
