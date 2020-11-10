/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.handlers;

import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.manager.PersonManager;
import co.logike.roots.market.core.api.objects.PersonDTO;
import co.logike.roots.market.core.app.entity.Person;
import co.logike.roots.market.core.app.parser.PersonParser;
import co.logike.roots.market.core.app.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler for {@link PersonManager}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Service
@Slf4j
public class PersonManagerHandler implements PersonManager {

    private final PersonRepository repository;

    @Autowired
    public PersonManagerHandler(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEvent<List<PersonDTO>> readAll() {
        log.debug("method: readAll()");
        try {
            List<PersonDTO> finalList = new ArrayList<>();
            List<Person> personList = repository.findAll();
            for (Person person : personList) {
                PersonDTO personDTO = PersonParser.setPersonDTO(person);
                finalList.add(personDTO);
            }
            return new ResponseEvent<List<PersonDTO>>().ok("Success", finalList);
        } catch (Exception ex) {
            log.error("method: read({}, {})", ex.getMessage(), ex);
            return new ResponseEvent<List<PersonDTO>>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<PersonDTO> read(QueryPKEvent<String> requestEvent) {
        log.info("method: read({})", requestEvent);
        try {
            if (requestEvent == null)
                return new ResponseEvent<PersonDTO>().badRequest("event is null.");
            if (requestEvent.getRequest() == null)
                return new ResponseEvent<PersonDTO>().badRequest("event.request is null.");
            final String id = requestEvent.getRequest();
            Long idL = Long.valueOf(id);
            Person person = repository.findByIdent(idL);
            PersonDTO personDTO = PersonParser.setPersonDTO(person);
            return new ResponseEvent<PersonDTO>().ok("Success", personDTO);
        } catch (Exception ex) {
            log.error("method: read({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<PersonDTO>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<PersonDTO> readByEmail(QueryPKEvent<String> requestEvent) {
        log.info("method: read({})", requestEvent);
        try {
            if (requestEvent == null)
                return new ResponseEvent<PersonDTO>().badRequest("event is null.");
            if (requestEvent.getRequest() == null)
                return new ResponseEvent<PersonDTO>().badRequest("event.request is null.");
            final String email = requestEvent.getRequest();
            Person person = repository.findByEmail(email);
            PersonDTO personDTO = PersonParser.setPersonDTO(person);
            return new ResponseEvent<PersonDTO>().ok("Success", personDTO);
        } catch (Exception ex) {
            log.error("method: read({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<PersonDTO>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<PersonDTO> create(CommandEvent<PersonDTO> requestEvent) {
        log.info("method: create({})", requestEvent);
        try {
            if (requestEvent == null) {
                return new ResponseEvent<PersonDTO>().badRequest("event is null.");
            }
            PersonDTO person = requestEvent.getRequest();

            Person entity = PersonParser.setPerson(person);
            repository.save(entity);
            repository.flush();
            person.setId(entity.getId().toString());
            return new ResponseEvent<PersonDTO>().ok("Success", person);
        } catch (Exception ex) {
            log.error("method: create({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<PersonDTO>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<PersonDTO> login(CommandEvent<PersonDTO> requestEvent) {
        log.info("method: login({})", requestEvent);
        try {
            if (requestEvent == null) {
                return new ResponseEvent<PersonDTO>().badRequest("event is null.");
            }
            //PersonDTO person = requestEvent.getRequest();
            String email = requestEvent.getRequest().getEmail();
            String pass = requestEvent.getRequest().getPassword();

            Person person = repository.findByEmailAndPass(email, pass);

            PersonDTO personDTO = PersonParser.setPersonDTO(person);

            return new ResponseEvent<PersonDTO>().ok("Success", personDTO);
        } catch (Exception ex) {
            log.error("method: create({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<PersonDTO>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<PersonDTO> update(CommandEvent<PersonDTO> requestEvent, String id) {
        log.info("method: update ({},{})", requestEvent, id);
        try {
            if (requestEvent != null) {
                PersonDTO person = requestEvent.getRequest();
                Person personToUpdate = repository.findByIdent(Long.valueOf(id));

                PersonParser.setPerson(personToUpdate, person);
                repository.save(personToUpdate);
                return new ResponseEvent<PersonDTO>().ok("Success", person);
            } else {
                return new ResponseEvent<PersonDTO>().badRequest("event is null.");
            }
        } catch (Exception ex) {
            log.error("method: update({},{})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<PersonDTO>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<String> delete(CommandEvent<String> requestEvent) {
        log.info("method: delete ({})", requestEvent);
        try {
            if (requestEvent != null) {
                final String personId = requestEvent.getRequest();
                Long ident = Long.valueOf(personId);
                Person personToDelete = repository.findByIdent(ident);
                repository.delete(personToDelete);
                return new ResponseEvent<String>().ok("Success", personId);
            } else {
                return new ResponseEvent<String>().badRequest("event is null.");
            }
        } catch (Exception ex) {
            log.error("method: delete({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<String>().conflict(ex.getMessage());
        }
    }

}
