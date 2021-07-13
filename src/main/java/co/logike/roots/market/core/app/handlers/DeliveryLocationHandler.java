/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.handlers;

import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.manager.DeliveryLocationManager;
import co.logike.roots.market.core.api.objects.DeliveryLocationDTO;
import co.logike.roots.market.core.api.objects.ProductDTO;
import co.logike.roots.market.core.app.entity.DeliveryLocation;
import co.logike.roots.market.core.app.entity.Product;
import co.logike.roots.market.core.app.parser.DeliveryLocationParser;
import co.logike.roots.market.core.app.parser.ProductParser;
import co.logike.roots.market.core.app.repository.DeliveryLocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler for {@link DeliveryLocation}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Service
@Slf4j
public class DeliveryLocationHandler implements DeliveryLocationManager {

    private final DeliveryLocationRepository repository;

    @Autowired
    public DeliveryLocationHandler(DeliveryLocationRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEvent<List<DeliveryLocationDTO>> readAll() {
        log.debug("method: readAll()");
        try {
            List<DeliveryLocationDTO> finalList = new ArrayList<>();
            List<DeliveryLocation> DeliveryLocationList = repository.findAll();
            for (DeliveryLocation DeliveryLocation : DeliveryLocationList) {
            	DeliveryLocationDTO DeliveryLocationDTO = DeliveryLocationParser.setDeliveryLocationDTO(DeliveryLocation);
                finalList.add(DeliveryLocationDTO);
            }
            return new ResponseEvent<List<DeliveryLocationDTO>>().ok("Success", finalList);
        } catch (Exception ex) {
            log.error("method: read({}, {})", ex.getMessage(), ex);
            return new ResponseEvent<List<DeliveryLocationDTO>>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<DeliveryLocationDTO> read(QueryPKEvent<String> requestEvent) {
        log.info("method: read({})", requestEvent);
        try {
            if (requestEvent == null)
                return new ResponseEvent<DeliveryLocationDTO>().badRequest("event is null.");
            if (requestEvent.getRequest() == null)
                return new ResponseEvent<DeliveryLocationDTO>().badRequest("event.request is null.");
            final String id = requestEvent.getRequest();
            Long idL = Long.valueOf(id);
            DeliveryLocation DeliveryLocation = repository.findByIdent(idL);
            DeliveryLocationDTO DeliveryLocationDTO = DeliveryLocationParser.setDeliveryLocationDTO(DeliveryLocation);
            return new ResponseEvent<DeliveryLocationDTO>().ok("Success", DeliveryLocationDTO);
        } catch (Exception ex) {
            log.error("method: read({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<DeliveryLocationDTO>().conflict(ex.getMessage());
        }
    }
    
    
    @Override
    public ResponseEvent<List<DeliveryLocationDTO>> findByOrganization(QueryPKEvent<String> requestEvent) {
        log.debug("method: readLast()");
        try {
            if (requestEvent == null)
                return new ResponseEvent<List<DeliveryLocationDTO>>().badRequest("event is null.");
            if (requestEvent.getRequest() == null)
                return new ResponseEvent<List<DeliveryLocationDTO>>().badRequest("event.request is null.");
            final String id = requestEvent.getRequest();
            Long idL = Long.valueOf(id);
            List<DeliveryLocationDTO> finalList = new ArrayList<>();
            List<DeliveryLocation> deliveryLocations = repository.findByOrganizationId(idL);
            for (DeliveryLocation deliveryLocation : deliveryLocations) {
            	DeliveryLocationDTO deliveryLocationDTO = DeliveryLocationParser.setDeliveryLocationDTO(deliveryLocation);
                finalList.add(deliveryLocationDTO);
            }
            return new ResponseEvent<List<DeliveryLocationDTO>>().ok("Success", finalList);
        } catch (Exception ex) {
            log.error("method: read({}, {})", ex.getMessage(), ex);
            return new ResponseEvent<List<DeliveryLocationDTO>>().conflict(ex.getMessage());
        }
    }

}
