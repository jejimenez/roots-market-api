/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.adapter;

import co.logike.roots.market.adapter.controller.MarketController;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Market controller test.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class MarketControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private MarketController marketController;

    @Before
    public void setUp() {
        log.info("MarketController mock -> @Before setUp ...");
        mockMvc = MockMvcBuilders.standaloneSetup(marketController).build();
    }

    @Test
    public void testRoot() throws Exception {
        log.info("Testing -> testRoot() ...");
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        log.info("Assert http status OK -> status code: OK");
        log.info("Assert http content -> ");
    }

    @Test
    public void testPing() throws Exception {
        log.info("Testing -> testPing() ...");
        mockMvc.perform(get("/ping"))
                .andExpect(status().isOk())
                .andExpect(content().string("Ping success."));
        log.info("Assert http status OK -> status code: OK");
        log.info("Assert http content -> Ping success");
    }

}
