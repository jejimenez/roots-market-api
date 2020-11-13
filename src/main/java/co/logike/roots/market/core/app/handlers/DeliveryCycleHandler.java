/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.handlers;

import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.manager.DeliveryCycleManager;
import co.logike.roots.market.core.api.objects.DeliveryCycleDTO;
import co.logike.roots.market.core.app.entity.DeliveryCycle;
import co.logike.roots.market.core.app.parser.DeliveryCycleParser;
import co.logike.roots.market.core.app.repository.DeliveryCycleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler for {@link DeliveryCycle}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Service
@Slf4j
public class DeliveryCycleHandler implements DeliveryCycleManager {

    private final DeliveryCycleRepository repository;

    @Autowired
    public DeliveryCycleHandler(DeliveryCycleRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEvent<List<DeliveryCycleDTO>> readAll() {
        log.debug("method: readAll()");
        try {
            List<DeliveryCycleDTO> finalList = new ArrayList<>();
            List<DeliveryCycle> personList = repository.findAll();
            for (DeliveryCycle person : personList) {
                DeliveryCycleDTO personDTO = DeliveryCycleParser.setDeliveryCycleDTO(person);
                finalList.add(personDTO);
            }
            return new ResponseEvent<List<DeliveryCycleDTO>>().ok("Success", finalList);
        } catch (Exception ex) {
            log.error("method: read({}, {})", ex.getMessage(), ex);
            return new ResponseEvent<List<DeliveryCycleDTO>>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<DeliveryCycleDTO> read(QueryPKEvent<String> requestEvent) {
        log.info("method: read({})", requestEvent);
        try {
            if (requestEvent == null)
                return new ResponseEvent<DeliveryCycleDTO>().badRequest("event is null.");
            if (requestEvent.getRequest() == null)
                return new ResponseEvent<DeliveryCycleDTO>().badRequest("event.request is null.");
            final String id = requestEvent.getRequest();
            Long idL = Long.valueOf(id);
            DeliveryCycle person = repository.findByIdent(idL);
            DeliveryCycleDTO personDTO = DeliveryCycleParser.setDeliveryCycleDTO(person);
            return new ResponseEvent<DeliveryCycleDTO>().ok("Success", personDTO);
        } catch (Exception ex) {
            log.error("method: read({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<DeliveryCycleDTO>().conflict(ex.getMessage());
        }
    }

}
