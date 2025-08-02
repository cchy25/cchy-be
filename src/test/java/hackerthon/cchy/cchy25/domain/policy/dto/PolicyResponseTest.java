package hackerthon.cchy.cchy25.domain.policy.dto;

import hackerthon.cchy.cchy25.domain.policy.entity.Policy;
import hackerthon.cchy.cchy25.domain.policy.entity.RegionCode;
import hackerthon.cchy.cchy25.domain.policy.entity.SupportField;
import hackerthon.cchy.cchy25.domain.policy.entity.SupportTarget;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PolicyResponseTest {

    @Test
    @DisplayName("정책 정확도 계산 테스트 - 소수점 계산 적용")
    void testCalcAccuracy() {
        // given
        PolicySearchRequest request = PolicySearchRequest.builder()
                .regions(Set.of(RegionCode.GWANGJU, RegionCode.SEOUL))
                .supportFields(Set.of(SupportField.TECH, SupportField.CULTURE))
                .supportTargets(Set.of(SupportTarget.PRE, SupportTarget.WOMEN))
                .build();

        Policy policy = Policy.builder()
                .title("테스트 정책")
                .summary("정확도 계산 테스트를 위한 정책입니다.")
                .url("http://example.com")
                .regions(Set.of(RegionCode.GWANGJU))
                .supportFields(Set.of(SupportField.TECH, SupportField.MANUFACTURE))
                .targets(Set.of(SupportTarget.PRE, SupportTarget.WOMEN, SupportTarget.SOLO))
                .build();

        // when
        PolicyResponse response = PolicyResponse.from(policy, request);
        Integer accuracy = response.getAccuracy();

        // then
        // 예상 정확도: ((1.0/2.0) + (1.0/2.0) + (2.0/2.0)) / 3 * 100 = 67
        assertEquals(67, accuracy);

        System.out.println("--- PolicyResponse.calcAccuracy() 단위 테스트 결과 ---");
        System.out.println("계산된 정확도: " + accuracy);
        System.out.println("예상 정확도: 67");
    }
}
