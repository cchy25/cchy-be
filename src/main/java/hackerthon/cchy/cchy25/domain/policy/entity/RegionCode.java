package hackerthon.cchy.cchy25.domain.policy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum RegionCode {
    ALL("전국"),
    GWANGJU("광주"),
    DAEGU("대구"),
    INCHEON("인천"),
    SEOUL("서울"),
    BUSAN("부산"),
    DAEJEON("대전"),
    ULSAN("울산"),
    SEJONG("세종"),
    GYEONGGI("경기"),
    GANGWON("강원"),
    CHUNGBUK("충북"),
    CHUNGNAM("충남"),
    JEONBUK("전북"),
    JEONNAM("전남"),
    GYEONGBUK("경북"),
    GYEONGNAM("경남"),
    JEJU("제주");

    private final String name;

    public static RegionCode fromString(String code) {
        return Arrays.stream(values())
                .filter(rc -> rc.getName().equalsIgnoreCase(code.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid region code: " + code));
    }

}