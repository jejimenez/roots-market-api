/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.config;

import co.logike.roots.Application;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Servlet Initializer for project.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}
