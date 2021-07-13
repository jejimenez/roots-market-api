/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.repository;

import co.logike.roots.market.core.app.entity.DeliveryLocation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for {@link DeliveryCycle}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-06
 * @since 1.0
 */
@Repository
public interface DeliveryLocationRepository extends JpaRepository<DeliveryLocation, Long> {

    @Query(value = "SELECT * FROM DELIVERY_LOCATION WHERE ID_ = :id", nativeQuery = true)
    DeliveryLocation findByIdent(@Param("id") Long id);

	List<DeliveryLocation> findByOrganizationId(@Param("organization") Long organization);
    
}
