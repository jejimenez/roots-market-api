/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.handlers;

import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.manager.PurchaseOrderManager;
import co.logike.roots.market.core.api.objects.ProductStatusDTO;
import co.logike.roots.market.core.api.objects.PurchaseOrderDTO;
import co.logike.roots.market.core.app.entity.OrderStatus;
import co.logike.roots.market.core.app.entity.Person;
import co.logike.roots.market.core.app.entity.ProductStatus;
import co.logike.roots.market.core.app.entity.PurchaseOrder;
import co.logike.roots.market.core.app.parser.ProductStatusParser;
import co.logike.roots.market.core.app.parser.PurchaseOrderParser;
import co.logike.roots.market.core.app.repository.OrderStatusRepository;
import co.logike.roots.market.core.app.repository.PersonRepository;
import co.logike.roots.market.core.app.repository.PurchaseOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler for {@link PurchaseOrderManager}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jiménez</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Service
@Slf4j
public class PurchaseOrderManagerHandler implements PurchaseOrderManager {

    private final PurchaseOrderRepository repository;
    private final PersonRepository personRepository;
    private final OrderStatusRepository orderStatusRepository;

    @Autowired
    public PurchaseOrderManagerHandler(PurchaseOrderRepository repository, PersonRepository personRepository,
    			OrderStatusRepository orderStatusRepository) {
        this.repository = repository;
        this.personRepository = personRepository;
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public ResponseEvent<List<PurchaseOrderDTO>> readAll() {
        log.debug("method: readAll()");
        try {
            List<PurchaseOrderDTO> finalList = new ArrayList<>();
            List<PurchaseOrder> purchaseOrderList = repository.findAll();
            for (PurchaseOrder purchaseOrder : purchaseOrderList) {
            	PurchaseOrderDTO purchaseOrderDTO = PurchaseOrderParser.setPurchaseOrderDTO(purchaseOrder);
                finalList.add(purchaseOrderDTO);
            }
            return new ResponseEvent<List<PurchaseOrderDTO>>().ok("Success", finalList);
        } catch (Exception ex) {
            log.error("method: read({}, {})", ex.getMessage(), ex);
            return new ResponseEvent<List<PurchaseOrderDTO>>().conflict(ex.getMessage());
        }
    }


    @Override
    public ResponseEvent<PurchaseOrderDTO> read(QueryPKEvent<String> requestEvent) {
        log.info("method: read({})", requestEvent);
        try {
            if (requestEvent == null)
                return new ResponseEvent<PurchaseOrderDTO>().badRequest("event is null.");
            if (requestEvent.getRequest() == null)
                return new ResponseEvent<PurchaseOrderDTO>().badRequest("event.request is null.");
            final String id = requestEvent.getRequest();
            Long idL = Long.valueOf(id);
            PurchaseOrder purchaseOrder = repository.findByIdent(idL);
            PurchaseOrderDTO purchaseOrderDTO = PurchaseOrderParser.setPurchaseOrderDTO(purchaseOrder);
            return new ResponseEvent<PurchaseOrderDTO>().ok("Success", purchaseOrderDTO);
        } catch (Exception ex) {
            log.error("method: read({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<PurchaseOrderDTO>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<List<PurchaseOrderDTO>> readByPerson(QueryPKEvent<String> requestEvent) {
        log.debug("method: readByPerson()");
        try {
            if (requestEvent == null)
                return new ResponseEvent<List<PurchaseOrderDTO>>().badRequest("event is null.");
            if (requestEvent.getRequest() == null)
                return new ResponseEvent<List<PurchaseOrderDTO>>().badRequest("event.request is null.");
            final String id = requestEvent.getRequest();
            Long idL = Long.valueOf(id);
            List<PurchaseOrderDTO> finalList = new ArrayList<>();
            List<PurchaseOrder> productList = repository.findByPerson(idL);
            for (PurchaseOrder productStatus : productList) {
            	PurchaseOrderDTO ProductStatusDTO = PurchaseOrderParser.setPurchaseOrderDTO(productStatus);
                finalList.add(ProductStatusDTO);
            }
            return new ResponseEvent<List<PurchaseOrderDTO>>().ok("Success", finalList);
        } catch (Exception ex) {
            log.error("method: read({}, {})", ex.getMessage(), ex);
            return new ResponseEvent<List<PurchaseOrderDTO>>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<PurchaseOrderDTO> create(CommandEvent<PurchaseOrderDTO> requestEvent) {
        log.info("method: create({})", requestEvent);
        try {
            if (requestEvent == null) {
                return new ResponseEvent<PurchaseOrderDTO>().badRequest("event is null.");
            }
            PurchaseOrderDTO purchaseOrder = requestEvent.getRequest();
            PurchaseOrderDTO finalPurchaseOrder;
            Person person = personRepository.findByIdent(Long.valueOf(purchaseOrder.getPerson()));
            OrderStatus orderStatus = orderStatusRepository.findByIdent(Long.valueOf(purchaseOrder.getOrderStatus()));

            PurchaseOrder entity = PurchaseOrderParser.setPurchaseOrder(purchaseOrder, person, orderStatus);
            repository.save(entity);
            repository.flush();
            //purchaseOrder.setId(entity.getId().toString());
            finalPurchaseOrder = PurchaseOrderParser.setPurchaseOrderDTO(entity);
            return new ResponseEvent<PurchaseOrderDTO>().ok("Success", finalPurchaseOrder);
        } catch (Exception ex) {
            log.error("method: create({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<PurchaseOrderDTO>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<PurchaseOrderDTO> update(CommandEvent<PurchaseOrderDTO> requestEvent, String id) {
        log.info("method: update ({},{})", requestEvent, id);
        try {
            if (requestEvent != null) {
                PurchaseOrderDTO purchaseOrder = requestEvent.getRequest();
                PurchaseOrder purchaseOrderToUpdate = repository.findByIdent(Long.valueOf(id));
                Person person = personRepository.findByIdent(Long.valueOf(purchaseOrder.getPerson()));
                OrderStatus orderStatus = orderStatusRepository.findByIdent(Long.valueOf(purchaseOrder.getOrderStatus()));

                PurchaseOrderParser.setPurchaseOrder(purchaseOrderToUpdate, purchaseOrder, person, orderStatus);
                repository.save(purchaseOrderToUpdate);
                return new ResponseEvent<PurchaseOrderDTO>().ok("Success", purchaseOrder);
            } else {
                return new ResponseEvent<PurchaseOrderDTO>().badRequest("event is null.");
            }
        } catch (Exception ex) {
            log.error("method: update({},{})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<PurchaseOrderDTO>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<String> delete(CommandEvent<String> requestEvent) {
        log.info("method: delete ({})", requestEvent);
        try {
            if (requestEvent != null) {
                final String purchaseOrderId = requestEvent.getRequest();
                Long ident = Long.valueOf(purchaseOrderId);
                PurchaseOrder purchaseOrderToDelete = repository.findByIdent(ident);
                repository.delete(purchaseOrderToDelete);
                return new ResponseEvent<String>().ok("Success", purchaseOrderId);
            } else {
                return new ResponseEvent<String>().badRequest("event is null.");
            }
        } catch (Exception ex) {
            log.error("method: delete({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<String>().conflict(ex.getMessage());
        }
    }

}
