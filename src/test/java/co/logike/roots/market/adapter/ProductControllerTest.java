/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.adapter;

import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.objects.ProductDTO;
import co.logike.roots.market.core.app.entity.Product;
import co.logike.roots.market.core.app.parser.ProductParser;
import co.logike.roots.market.core.app.repository.ProductRepository;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Product controller test.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Slf4j
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for restTemplate
public class ProductControllerTest {

    private static final String URL = "/market/v1/product";

    private ResponseEvent<List<ProductDTO>> expectedEvent;

    @MockBean
    private ProductRepository mockRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void init() {
        log.info("ProductRepository mock -> @Before init ...");
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Product 1");

        Product product2 = new Product();
        product2.setProductId(2);
        product2.setProductName("Product 2");
        List<Product> productList = Arrays.asList(product, product2);

        when(mockRepository.findAll()).thenReturn(productList);
        log.info("Product mockRepository created -> findAll: {} ", productList.toString());

        List<ProductDTO> responseList = productList.stream().map(ProductParser::setProductDTO).collect(Collectors.toList());
        expectedEvent = new ResponseEvent<List<ProductDTO>>().ok("Success", responseList);
        log.info("Success event expected -> created: {} ", expectedEvent.toString());
    }

    @Test
    public void findAllProducts() throws Exception {
        log.info("Testing -> findAllProducts() ...");
        Gson gson = new Gson();
        ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
        ResponseEvent responseEvent = gson.fromJson(response.getBody(), ResponseEvent.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        log.info("Assert http status OK -> status code: {} ", response.getStatusCode());
        JSONAssert.assertEquals(expectedEvent.toString(), responseEvent.toString(), false);
        log.info("Assert expected json -> response: {} ", responseEvent.toString());
        verify(mockRepository, times(1)).findAll();
    }
}