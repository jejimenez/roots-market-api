/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.repository;

import co.logike.roots.market.core.app.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for {@link Category}.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM CATEGORY WHERE ID_ = :id", nativeQuery = true)
    Category findByIdent(@Param("id") Long id);
    
    @Query(value = "SELECT * FROM CATEGORY C WHERE EXISTS (SELECT 1 FROM PRODUCT WHERE CATEGORY_ = C.ID_ AND DELETED_ = FALSE)", nativeQuery = true)
    Category findAllByNotEmpty();
}
