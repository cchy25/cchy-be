package hackerthon.cchy.cchy25.domain.policy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ApplyMethod {
    EMAIL("이메일"),
    ONLINE("온라인"),
    VISIT("방문"),
    ETC("기타");

    private String name;

    public static ApplyMethod fromString(String s) {
        return Arrays.stream(values())
                .filter(rc -> rc.getName().equalsIgnoreCase(s.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid ApplyMethod: " + s));
    }
}
