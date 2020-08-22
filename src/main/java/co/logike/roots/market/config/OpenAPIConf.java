/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Open API configuration.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Slf4j
@Configuration
public class OpenAPIConf {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("DeRaíz Market API")
                        .description("Market API Microservices"));
    }

}
