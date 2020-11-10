/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.repository;

import co.logike.roots.market.core.app.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for {@link Person}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-06
 * @since 1.0
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(value = "SELECT * FROM PERSON WHERE ID_ = :id", nativeQuery = true)
    Person findByIdent(@Param("id") Long id);
    
    @Query(value = "SELECT * FROM PERSON WHERE EMAIL_ = :email", nativeQuery = true)
    Person findByEmail(@Param("email") String email);
    
    @Query(value = "SELECT * FROM PERSON WHERE EMAIL_ = :email and PASSWORD_ = :pass ORDER BY ID_ DESC LIMIT 1", nativeQuery = true)
    Person findByEmailAndPass(@Param("email") String email, @Param("pass") String pass);
}
