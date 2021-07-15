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

    @Query(value = "select row_number() over(order by cast(op.purchase_order_ as text), pr2.name_) as id, \n" + 
    		"cast(op.purchase_order_ as text) purchase_order,\n" + 
    		"pr2.name_ as client,\n" + 
    		"p.name_ as product , op.units_ as units, p.producer_cost_ as cost , \n" + 
    		"p.cost_ as price, \n" + 
    		"pr1.name_ as producer, \n" + 
    		"date(po.creation_time_) creation_time \n" + 
    		"from order_product op \n" + 
    		"inner join purchase_order po on op.purchase_order_ = po.id_ \n" + 
    		"inner join product p on op.product_ = p.id_ \n" + 
    		"inner join person_role_organization pro on p.producer_ = pro.id_ \n" + 
    		"inner join person pr1 on pro.person_ = pr1.id_ \n" + 
    		"inner join person pr2 on po.person_ = pr2.id_ \n" +
    		"where po.creation_time_ between cast(:sDate AS timestamp) AND cast(:eDate AS timestamp) " +
    		"order by purchase_order, client;", nativeQuery = true)
    List<OrderReport> findOrders(@Param("sDate") String sDate, @Param("eDate") String eDate);

}
