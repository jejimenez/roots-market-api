/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.repository;

import co.logike.roots.market.core.app.entity.Person;

import java.util.List;

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
    
    @Query(value = "SELECT * FROM PERSON WHERE EMAIL_ = :email and PASSWORD_ = crypt(:pass, PASSWORD_) ORDER BY ID_ DESC LIMIT 1", nativeQuery = true)
    Person findByEmailAndPass(@Param("email") String email, @Param("pass") String pass);

    @Query(value = "SELECT P.* FROM PERSON P JOIN PERSON_ROLE_ORGANIZATION PL ON P.ID_ = PL.PERSON_ \r\n" + 
    		"WHERE PL.ORGANIZATION_ = (SELECT PL.ORGANIZATION_ \r\n" + 
    		"			FROM PERSON_ROLE_ORGANIZATION PL JOIN PRODUCT P ON PL.ID_ = P.PRODUCER_ \r\n" + 
    		"			WHERE P.ID_ = :id limit  1)\r\n" + 
    		"  AND PL.ROLE_ = 1;", nativeQuery = true)
    List<Person> findOrgAdminByProduct(@Param("id") Long id);

    @Query(value = "SELECT P.* FROM PERSON P JOIN PERSON_ROLE_ORGANIZATION PL ON P.ID_ = PL.PERSON_ \r\n" + 
    		"WHERE PL.ORGANIZATION_ = (SELECT PL.ORGANIZATION_ \r\n" + 
    		"			FROM PERSON_ROLE_ORGANIZATION PL JOIN PRODUCT P ON PL.ID_ = P.PRODUCER_ \r\n" + 
    		"			JOIN ORDER_PRODUCT OP ON OP.PRODUCT_ = P.ID_\r\n" + 
    		"			WHERE OP.PURCHASE_ORDER_ = :id limit  1)\r\n" + 
    		"  AND PL.ROLE_ = 1;", nativeQuery = true)
    List<Person> findOrgAdminByPurchaseOrd(@Param("id") Long id);

    @Query(value = "SELECT P.*\r\n" + 
    		"FROM PERSON P JOIN PURCHASE_ORDER PO ON P.ID_ = PO.PERSON_ \r\n" + 
    		"WHERE PO.ID_ = :id limit  1;", nativeQuery = true)
    Person findByPurchaseOrd(@Param("id") Long id);
    
    
}
