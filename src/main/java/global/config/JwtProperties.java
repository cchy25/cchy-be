package global.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "jwt")
@Getter
@Component
public class JwtProperties {

    private String secretKey;
    private Long accessTokenExp;
}
