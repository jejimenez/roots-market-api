/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.parser;

import co.logike.roots.market.core.api.objects.ProductDTO;
import co.logike.roots.market.core.app.entity.Category;
import co.logike.roots.market.core.app.entity.PersonRoleOrganization;
import co.logike.roots.market.core.app.entity.Product;
import co.logike.roots.market.core.app.entity.Unit;

import java.math.BigDecimal;

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
        domain.setDescription(entity.getDescription());
        domain.setName(entity.getName());
        domain.setId(entity.getId().toString());
        domain.setCategory(entity.getCategory().getName());
        domain.setCost(entity.getCost() == null ? "" : entity.getCost().toString());
        domain.setLabels(entity.getLabels());
        domain.setProducer(entity.getProducer().getOrganization().getName());
        domain.setQuantity(entity.getQuantity() == null ? "" : entity.getQuantity().toString());
        domain.setUnit(entity.getUnit().getName());
        return domain;
    }

    public static Product setProduct(ProductDTO domain, Category category, Unit unit, PersonRoleOrganization personRoleOrganization) {
        Product entity = new Product();
        return getProduct(entity, domain, category, unit, personRoleOrganization);
    }

    public static void setProduct(Product productToUpdate, ProductDTO domain, Category category, Unit unit, PersonRoleOrganization personRoleOrganization) {
        getProduct(productToUpdate, domain, category, unit, personRoleOrganization);
    }

    private static Product getProduct(Product productToUpdate, ProductDTO domain, Category category, Unit unit, PersonRoleOrganization personRoleOrganization) {
        productToUpdate.setName(domain.getName());
        productToUpdate.setDescription(domain.getDescription());
        productToUpdate.setCategory(category);
        productToUpdate.setCost(new BigDecimal(domain.getCost()) );
        productToUpdate.setLabels(domain.getLabels());
        productToUpdate.setUnit(unit);
        productToUpdate.setProducer(personRoleOrganization);
        productToUpdate.setQuantity(new BigDecimal(domain.getQuantity()));
        return productToUpdate;
    }
}
