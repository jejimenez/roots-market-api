/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.repository;

import co.logike.roots.market.core.app.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for {@link Product}.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
//
//    @Query(value = "SELECT * FROM PRODUCT WHERE NAME = :productName", nativeQuery = true)
//    Product findByName(@Param("productName") String productName);
//
//    @Query(value = "SELECT * FROM PRODUCT WHERE IDENT = :ident", nativeQuery = true)
//    Product findByIdent(@Param("ident") Long ident);
}
