/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.adapter.controller;

import co.logike.roots.market.core.api.constants.CountryEnum;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Basic Rest controller for the app.
 * 
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Slf4j
@RestController
public class  MarketController {

  @Value("${msg.welcome}")
  private String welcomeMessage;

  @GetMapping("/")
  public String root() {
    log.debug("method: root()");
    log.debug("Locales: " + CountryEnum.CO + ", " + CountryEnum.DE);
    return welcomeMessage;
  }

  @GetMapping("/ping")
  public String ping() {
    log.debug("method: ping()");
    return "Ping success.";
  }

}
