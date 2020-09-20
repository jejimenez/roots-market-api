/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.repository;

import co.logike.roots.market.core.app.entity.Product;
import co.logike.roots.market.core.app.entity.PurchaseOrder;

import java.util.List;

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
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
	
    @Query(value = "SELECT * FROM PURCHASE_ORDER WHERE ID_ = :id", nativeQuery = true)
    PurchaseOrder findByIdent(@Param("id") Long id);
	
    @Query(value = "SELECT * FROM PURCHASE_ORDER WHERE PERSON_ = :id", nativeQuery = true)
    List<PurchaseOrder> findByPerson(@Param("id") Long id);
    
}
