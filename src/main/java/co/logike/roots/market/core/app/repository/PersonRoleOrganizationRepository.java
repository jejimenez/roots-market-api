/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.repository;

import co.logike.roots.market.core.app.entity.PersonRoleOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for {@link PersonRoleOrganization}.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Repository
public interface PersonRoleOrganizationRepository extends JpaRepository<PersonRoleOrganization, Long> {

    @Query(value = "SELECT * FROM PERSON_ROLE_ORGANIZATION WHERE ID_ = :id", nativeQuery = true)
    PersonRoleOrganization findByIdent(@Param("id") Long id);

}
