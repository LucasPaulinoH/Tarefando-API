package api.tutoringschool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI getOpenAPISettings() {
        return new OpenAPI().info(new Info()
                .title("Tarefando API")
                .version("v1")
                .description(
                        "An API designed for a mobile app focused on academic tasks tracking for primary education and tutoring.")
                .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.txt")));
    }
}
