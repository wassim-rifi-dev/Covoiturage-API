package ma.youcode.rideflow.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI rideFlowOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("RideFlow API")
                                .description("REST API documentation for RideFlow")
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .name("Wassim Rifi")
                                                .email("wassimrifi.dev@gmail.com")
                                                .url("https://wassimrifi.vercel.app/")
                                )
                );
    }
}
