/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.adapter;

import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.objects.ProductDTO;
import co.logike.roots.market.core.app.entity.Category;
import co.logike.roots.market.core.app.entity.Organization;
import co.logike.roots.market.core.app.entity.PersonRoleOrganization;
import co.logike.roots.market.core.app.entity.Product;
import co.logike.roots.market.core.app.entity.Unit;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
        PersonRoleOrganization perOrg = new PersonRoleOrganization();
        perOrg.setOrganization(new Organization());
        product.setId(1L);
        product.setName("Product 1");
        product.setCategory(new Category());
        product.setUnit(new Unit());
        product.setProducer(perOrg);
       
        Product product2 = new Product();
        PersonRoleOrganization perOrg2 = new PersonRoleOrganization();
        perOrg2.setOrganization(new Organization());
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setCategory(new Category());
        product2.setUnit(new Unit());
        product2.setProducer(perOrg2);
        
        List<Product> productList = Arrays.asList(product, product2);

        when(mockRepository.findAll()).thenReturn(productList);
        log.info("Product mockRepository created -> findAll: {} ", productList.toString());
        //List<ProductDTO> responseList = productList.stream().map(ProductParser::setProductDTO).collect(Collectors.toList());
        List<ProductDTO> responseList = new ArrayList<>();
        for (Product prod : productList) {
            ProductDTO productDTO = ProductParser.setProductDTO(prod);
            responseList.add(productDTO);
        }
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
