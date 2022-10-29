/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.repository;

import co.logike.roots.market.core.app.entity.CategoryGroup;
import co.logike.roots.market.core.app.entity.DeliveryLocation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for {@link Category}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jiménez</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Repository
public interface CategoryGroupRepository extends JpaRepository<CategoryGroup, Long> {

    @Query(value = "SELECT * FROM CATEGORY_GROUP WHERE ID_ = :id", nativeQuery = true)
    CategoryGroup findByIdent(@Param("id") Long id);
    
    @Query(value = "SELECT * FROM CATEGORY_GROUP C WHERE DELETED_ = FALSE ORDER BY ORDER_NUM_ ASC", nativeQuery = true)
    List<CategoryGroup> findAllByNotDeleted();

	List<CategoryGroup> findByOrganizationId(@Param("organization") Long organization);
    
}
