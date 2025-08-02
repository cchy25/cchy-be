package hackerthon.cchy.cchy25.domain.policy.entity;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum ApplyMethod {
    EMAIL("이메일"),
    ONLINE("온라인"),
    VISIT("방문");

    private String name;

    public static ApplyMethod fromString(String s) {
        return Arrays.stream(values())
                .filter(rc -> rc.name().equalsIgnoreCase(s.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid ApplyMethod: " + s));
    }
}
