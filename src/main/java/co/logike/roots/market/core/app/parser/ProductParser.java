/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.parser;

import co.logike.roots.market.core.api.objects.ProductDTO;
import co.logike.roots.market.core.app.entity.Product;

/**
 * Product produced parser.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
public class ProductParser {

    public static ProductDTO setProductDTO(Product entity) {
        ProductDTO domain = new ProductDTO();
        domain.setId(entity.getProductId().toString());
        domain.setName(entity.getProductName());
        domain.setDescription(entity.getDescription());
        return domain;
    }

}
