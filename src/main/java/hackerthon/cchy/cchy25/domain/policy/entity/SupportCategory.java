package hackerthon.cchy.cchy25.domain.policy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum SupportCategory {
    FUNDING("재정지원"),
    PROTOTYPE("시제품제작"),
    SPACE("공간지원"),
    TUTOR("교육"),
    NETWORK("네트워킹"),
    MENTORING("멘토링"),
    TEAM("창업팀빌딩"),
    ADMIN("행정지원"),
    INVESTMENT("투자유치"),
    MARKETING("마케팅/판로개척"),
    HR("인력/고용"),
    GLOBAL("글로벌"),
    ETC("기타");

    private String name;

    public static SupportCategory fromString(String s) {
        return Arrays.stream(values())
                .filter(rc -> rc.getName().equalsIgnoreCase(s.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid category: " + s));
    }
}
