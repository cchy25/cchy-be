package hackerthon.cchy.cchy25.domain.policy.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ApplyMethod {
    EMAIL("이메일"),
    ONLINE("온라인"),
    VISIT("방문");

    private String name;
}
