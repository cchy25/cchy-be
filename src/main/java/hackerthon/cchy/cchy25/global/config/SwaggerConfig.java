package hackerthon.cchy.cchy25.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        servers = {
                @Server(url = "https://api.qstart.xyz", description = "운영 서버"),
                @Server(url = "http://localhost:8080", description = "개발 서버")
        })
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CCHY API 문서")
                        .description("청정해요 Qstart Swagger 문서입니다.")
                        .version("0.1")
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addResponses("403", new ApiResponse().description("권한 없음"))
                        .addResponses("404", new ApiResponse().description("리소스 없음"))
                        .addResponses("201", new ApiResponse().description("리로스 생성"))
                        .addResponses("204", new ApiResponse().description("삭제 성공"))
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }

}