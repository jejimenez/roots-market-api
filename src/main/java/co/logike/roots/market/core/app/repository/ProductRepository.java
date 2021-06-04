/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.repository;

import co.logike.roots.market.core.app.entity.Product;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

/**
 * JPA repository for {@link Product}.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM PRODUCT WHERE ID_ = :id", nativeQuery = true)
    Product findByIdent(@Param("id") Long id);

    @Query(value = "SELECT P.* FROM PRODUCT P JOIN ORDER_PRODUCT OP ON P.ID_ = OP.PRODUCT_ JOIN PURCHASE_ORDER PO ON OP.PURCHASE_ORDER_ = PO.ID_ WHERE PO.ID_ = :id", nativeQuery = true)
    List<Product> findByPurchaseOrder(@Param("id") Long id);
}
