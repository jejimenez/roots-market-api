/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.parser;


import java.math.BigDecimal;

import co.logike.roots.market.core.api.objects.OrderProductDTO;
import co.logike.roots.market.core.api.objects.OrderProductMailedDTO;
import co.logike.roots.market.core.app.entity.OrderStatus;
import co.logike.roots.market.core.app.entity.Person;
import co.logike.roots.market.core.app.entity.Product;
import co.logike.roots.market.core.app.entity.ProductStatus;
import co.logike.roots.market.core.app.entity.PurchaseOrder;
import co.logike.roots.market.core.app.entity.OrderProduct;
/**
 * Purchase Order parser.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jiménez</a>
 * @version 1.0 2020-09-19
 * @since 1.0
 */
public class OrderProductParser{

    public static OrderProductDTO setOrderProductDTO(OrderProduct entity) {
        OrderProductDTO domain = new OrderProductDTO();
        domain.setId(entity.getId() == null ? "" : entity.getId().toString());
        domain.setPurchaseOrder((entity.getPurchaseOrder() == null || entity.getPurchaseOrder().getId() == null) ? "" : entity.getPurchaseOrder().getId().toString());
        domain.setProduct(entity.getProduct().getName());
        //domain.setProduct(entity.getProduct() == null ? "" : entity.getProduct().getName() + " " + entity.getProduct().getQuantity() + (entity.getProduct().getUnit() == null ? "" : entity.getProduct().getUnit().getName()));
        domain.setProductStatus(entity.getProductStatus() == null ? "" : entity.getProductStatus().getName());
        domain.setUnits(entity.getUnits() == null ? "" : entity.getUnits().toString());
        domain.setDescription(entity.getDescription());
        return domain;
    }
    
    public static OrderProductMailedDTO setOrderProductDTO(OrderProduct entity, Product product) {
    	OrderProductMailedDTO domain = new OrderProductMailedDTO();
        domain.setId(entity.getId() == null ? "" : entity.getId().toString());
        domain.setPurchaseOrder((entity.getPurchaseOrder() == null || entity.getPurchaseOrder().getId() == null) ? "" : entity.getPurchaseOrder().getId().toString());
        domain.setProduct(entity.getProduct().getName());
        //domain.setProduct(entity.getProduct() == null ? "" : entity.getProduct().getName() + " " + entity.getProduct().getQuantity() + (entity.getProduct().getUnit() == null ? "" : entity.getProduct().getUnit().getName()));
        domain.setProductStatus(entity.getProductStatus() == null ? "" : entity.getProductStatus().getName());
        domain.setUnits(entity.getUnits() == null ? "" : entity.getUnits().toString());
        domain.setDescription(entity.getDescription());

        domain.setQuantity(product.getQuantity() == null ? "" : product.getQuantity().toString());
        domain.setUnit(product.getUnit().getName() == null ? "" : product.getUnit().getName().toString());
        
        return domain;
    }

    public static OrderProduct setOrderProduct(OrderProductDTO domain, PurchaseOrder purchaseOrder, Product product, ProductStatus productStatus) {
    	OrderProduct entity = new OrderProduct();
        return getOrderProduct(entity, domain, purchaseOrder, product, productStatus);
    }

    public static void setOrderProduct(OrderProduct purchaseOrderToUpdate, OrderProductDTO domain, PurchaseOrder purchaseOrder, Product product, ProductStatus productStatus) {
        getOrderProduct(purchaseOrderToUpdate, domain, purchaseOrder, product, productStatus);
    }

    private static OrderProduct getOrderProduct(OrderProduct purchaseOrderToUpdate, OrderProductDTO domain, PurchaseOrder purchaseOrder, Product product, ProductStatus productStatus) {
        purchaseOrderToUpdate.setPurchaseOrder(purchaseOrder);
        purchaseOrderToUpdate.setProduct(product);
        purchaseOrderToUpdate.setProductStatus(productStatus);
        purchaseOrderToUpdate.setUnits(new BigDecimal(domain.getUnits()));
        purchaseOrderToUpdate.setDescription(domain.getDescription());
        return purchaseOrderToUpdate;
    }
}
