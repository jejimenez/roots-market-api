/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.parser;

import co.logike.roots.market.core.api.objects.PersonDTO;
import co.logike.roots.market.core.app.entity.Person;

import java.math.BigDecimal;

/**
 * Person produced parser.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-06
 * @since 1.0
 */
public class PersonParser {

    public static PersonDTO setPersonDTO(Person entity) {
        PersonDTO domain = new PersonDTO();
        domain.setId(entity.getId().toString());
        domain.setNickname(entity.getNickname());
        domain.setEmail(entity.getEmail());
        domain.setName(entity.getName());
        domain.setPassword(entity.getPassword());
        domain.setAddress(entity.getAddress());
        domain.setMapLatitude(entity.getMapLatitude().toString());
        domain.setMapLongitude(entity.getMapLongitude().toString());
        domain.setEthKey(entity.getEthKey());
        domain.setEthAddress(entity.getEthAddress());
        
        return domain;
    }

    public static Person setPerson(PersonDTO domain) {
        Person entity = new Person();
        return getPerson(entity, domain);
    }

    public static void setPerson(Person personToUpdate, PersonDTO domain) {
        getPerson(personToUpdate, domain);
    }

    private static Person getPerson(Person personToUpdate, PersonDTO domain) {
    	personToUpdate.setNickname(domain.getNickname());
        personToUpdate.setEmail(domain.getEmail());
        personToUpdate.setName(domain.getName());
        personToUpdate.setPassword(domain.getPassword());
        personToUpdate.setAddress(domain.getAddress());
        personToUpdate.setMapLatitude(new BigDecimal(domain.getMapLatitude()));
        personToUpdate.setMapLongitude(new BigDecimal(domain.getMapLongitude()));
        personToUpdate.setEthKey(domain.getEthKey());
        personToUpdate.setEthAddress(domain.getEthAddress());
        return personToUpdate;
    }
}
