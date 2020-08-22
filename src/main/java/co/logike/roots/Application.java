/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Application class for project.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        log.debug("Available Beans:");
        for (String name : applicationContext.getBeanDefinitionNames())
            log.debug(name);
    }

}
