/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.handlers;

import co.logike.roots.market.core.api.events.QueryEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.manager.ProductManager;
import co.logike.roots.market.core.api.objects.ProductDTO;
import co.logike.roots.market.core.app.entity.Product;
import co.logike.roots.market.core.app.parser.ProductParser;
import co.logike.roots.market.core.app.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler for {@link ProductManager}.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Service
@Slf4j
public class ProductManagerHandler implements ProductManager {

    private final ProductRepository repository;

    @Autowired
    public ProductManagerHandler(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEvent<List<ProductDTO>> read(QueryEvent requestEvent) {
        log.info("method: read({})", requestEvent);
        try {
            if (requestEvent == null)
                return new ResponseEvent<List<ProductDTO>>().badRequest("event is null.");

            List<ProductDTO> finalList = new ArrayList<>();
            List<Product> productList = repository.findAll();
            for (Product product : productList) {
                ProductDTO productDTO = ProductParser.setProductDTO(product);
                finalList.add(productDTO);
            }
            return new ResponseEvent<List<ProductDTO>>().ok("Success", finalList);
        } catch (Exception ex) {
            log.error("method: read({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<List<ProductDTO>>().conflict(ex.getMessage());
        }
    }

}
