/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.repository;

import co.logike.roots.market.core.app.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for {@link ProductStatus}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-05
 * @since 1.0
 */
@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {

    @Query(value = "SELECT * FROM ORDER_STATUS WHERE ID_ = :id", nativeQuery = true)
    OrderStatus findByIdent(@Param("id") Long id);
}
