/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.repository;

import co.logike.roots.market.core.app.entity.OrderProduct;
import co.logike.roots.market.core.app.entity.OrderReport;
import co.logike.roots.market.core.app.entity.Product;
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
public interface OrderReportRepository extends JpaRepository<OrderReport, String> {

    @Query(value = "select cast(op.id_ as text)||cast(op.purchase_order_ as text) as orden_de_compra, p.name_ as producto , op.units_ as cantidad, p.producer_cost_ as costo , o.name_ as productor from order_product op inner join purchase_order po on op.purchase_order_ = po.id_ inner join product p on op.product_ = p.id_ inner join person_role_organization pro on p.producer_ = pro.id_ inner join organization o on pro.organization_ = o.id_ where po.creation_time between cast(:sDate AS timestamp) AND cast(:eDate AS timestamp) order by o.name_, p.name_;", nativeQuery = true)
    List<OrderReport> findOrders(@Param("sDate") String sDate, @Param("eDate") String eDate);

}
