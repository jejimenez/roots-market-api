/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.parser;

import co.logike.roots.market.core.api.objects.ProductStatusDTO;
import co.logike.roots.market.core.app.entity.Category;
import co.logike.roots.market.core.app.entity.PersonRoleOrganization;
import co.logike.roots.market.core.app.entity.ProductStatus;
import co.logike.roots.market.core.app.entity.Unit;


/**
 * Product produced parser.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-05
 * @since 1.0
 */
public class ProductStatusParser {

    public static ProductStatusDTO setProductStatusDTO(ProductStatus entity) {
    	ProductStatusDTO domain = new ProductStatusDTO();
        domain.setName(entity.getName());
        domain.setId(entity.getId().toString());
        return domain;
    }

    public static ProductStatus setProductStatus(ProductStatusDTO domain) {
    	ProductStatus entity = new ProductStatus();
        return getProductStatus(entity, domain);
    }

    public static void setProductStatus(ProductStatus productStatusToUpdate, ProductStatusDTO domain) {
        getProductStatus(productStatusToUpdate, domain);
    }

    private static ProductStatus getProductStatus(ProductStatus productStatusToUpdate, ProductStatusDTO domain) {
    	productStatusToUpdate.setName(domain.getName());
        return productStatusToUpdate;
    }
}
