package hackerthon.cchy.cchy25.domain.policy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PolicyStatus {
    APPLIED("지원완료"),
    CHOSEN("선발"),
    DROPPED("중도포기");

    private String name;
}

