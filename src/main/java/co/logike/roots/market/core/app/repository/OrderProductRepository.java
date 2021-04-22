/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.repository;

import co.logike.roots.market.core.app.entity.OrderProduct;
import co.logike.roots.market.core.app.entity.OrderReport;
import co.logike.roots.market.core.app.entity.Product;
import co.logike.roots.market.core.app.entity.PurchaseOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository for {@link Product}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jiménez</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    @Query(value = "SELECT * FROM ORDER_PRODUCT WHERE ID_ = :id", nativeQuery = true)
    OrderProduct findByIdent(@Param("id") Long id);


    @Query(value = "SELECT OP.* FROM ORDER_PRODUCT OP JOIN PURCHASE_ORDER PO ON OP.PURCHASE_ORDER_ = PO.ID_ WHERE PO.ID_ = :id", nativeQuery = true)
    OrderProduct findByPurchaseOrder(@Param("id") Long id);

    @Query(value = "select op.id_ , p.name_ as product , op.units_ , p.cost_ , o.name_ as organization from order_product op inner join purchase_order po on op.purchase_order_ = po.id_ inner join product p on op.product_ = p.id_ inner join person_role_organization pro on p.producer_ = pro.id_ inner join organization o on pro.organization_ = o.id_ order by o.name_, p.name_", nativeQuery = true)
    List<OrderReport> findOrders();

}
