/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.repository;

import co.logike.roots.market.core.app.entity.OrderProduct;
import co.logike.roots.market.core.app.entity.Product;
import co.logike.roots.market.core.app.entity.PurchaseOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

}
