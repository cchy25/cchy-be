package hackerthon.cchy.cchy25.global.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "jwt")
@Getter
@AllArgsConstructor
public class JwtProperties {

    private final String secretKey;
    private final Long accessTokenExp;
}

