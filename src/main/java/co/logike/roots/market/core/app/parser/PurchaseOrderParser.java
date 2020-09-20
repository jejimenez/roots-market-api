/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.parser;


import co.logike.roots.market.core.api.objects.PurchaseOrderDTO;
import co.logike.roots.market.core.app.entity.OrderStatus;
import co.logike.roots.market.core.app.entity.Person;
import co.logike.roots.market.core.app.entity.PurchaseOrder;
/**
 * Purchase Order parser.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jiménez</a>
 * @version 1.0 2020-09-19
 * @since 1.0
 */
public class PurchaseOrderParser {

    public static PurchaseOrderDTO setPurchaseOrderDTO(PurchaseOrder entity) {
        PurchaseOrderDTO domain = new PurchaseOrderDTO();
        domain.setId(entity.getId().toString());
        domain.setPerson(entity.getPerson().getName());
        domain.setOrderStatus(entity.getOrderStatus().getName());
        domain.setRequest(entity.getRequest());
        return domain;
    }

    public static PurchaseOrder setPurchaseOrder(PurchaseOrderDTO domain, Person person, OrderStatus orderStatus) {
    	PurchaseOrder entity = new PurchaseOrder();
        return getPurchaseOrder(entity, domain, person, orderStatus);
    }

    public static void setPurchaseOrder(PurchaseOrder purchaseOrderToUpdate, PurchaseOrderDTO domain, Person person, OrderStatus orderStatus) {
        getPurchaseOrder(purchaseOrderToUpdate, domain, person, orderStatus);
    }

    private static PurchaseOrder getPurchaseOrder(PurchaseOrder purchaseOrderToUpdate, PurchaseOrderDTO domain, Person person, OrderStatus orderStatus) {
        purchaseOrderToUpdate.setPerson(person);
        purchaseOrderToUpdate.setOrderStatus(orderStatus);
        purchaseOrderToUpdate.setRequest(domain.getRequest());
        return purchaseOrderToUpdate;
    }
}
