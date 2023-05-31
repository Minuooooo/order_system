package jpabook.jpashop.config.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {

        final String securitySchemeName = "bearerAuth";

        Info info = new Info()
                .title("Order API")
                .version("v1.0.0")
                .description("for order application frontend");

        return new OpenAPI()  // JWT Token 인증 방식을 사용하기 위해
                .addServersItem(new Server().url("/"))  // 접근하는 Url 과 Swagger 에서 API 요청 Url 을 통일
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .info(info);
    }
}
