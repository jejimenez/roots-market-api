package co.logike.roots.market.core.app.repository;

import co.logike.roots.market.core.app.entity.Person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for {@link OrganizationRepository}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-06
 * @since 1.0
 */
@Repository
public interface OrganizationRepository extends JpaRepository<Person, Long> {

    @Query(value = "SELECT * FROM ORGANIZATION O JOIN PERSON_ROLE_ORGANIZATION PL ON O.ID_ = PL.ORGANIZATION_ JOIN PRODUCT P ON PL.ID_ = P.PRODUCER_ WHERE P.ID_ = :id", nativeQuery = true)
    Person findByProduct(@Param("id") Long id);
    
}
