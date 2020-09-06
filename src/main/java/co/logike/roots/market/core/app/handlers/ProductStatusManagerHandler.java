/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.handlers;

import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.manager.ProductStatusManager;
import co.logike.roots.market.core.api.objects.ProductStatusDTO;
import co.logike.roots.market.core.app.entity.ProductStatus;
import co.logike.roots.market.core.app.parser.ProductStatusParser;
import co.logike.roots.market.core.app.repository.ProductStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler for {@link ProductManager}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Service
@Slf4j
public class ProductStatusManagerHandler implements ProductStatusManager {

    private final ProductStatusRepository repository;
    
    @Autowired
    public ProductStatusManagerHandler(ProductStatusRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEvent<List<ProductStatusDTO>> readAll() {
        log.debug("method: readAll()");
        try {
            List<ProductStatusDTO> finalList = new ArrayList<>();
            List<ProductStatus> productList = repository.findAll();
            for (ProductStatus productStatus : productList) {
                ProductStatusDTO ProductStatusDTO = ProductStatusParser.setProductStatusDTO(productStatus);
                finalList.add(ProductStatusDTO);
            }
            return new ResponseEvent<List<ProductStatusDTO>>().ok("Success", finalList);
        } catch (Exception ex) {
            log.error("method: read({}, {})", ex.getMessage(), ex);
            return new ResponseEvent<List<ProductStatusDTO>>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<ProductStatusDTO> read(QueryPKEvent<String> requestEvent) {
        log.info("method: read({})", requestEvent);
        try {
            if (requestEvent == null)
                return new ResponseEvent<ProductStatusDTO>().badRequest("event is null.");
            if (requestEvent.getRequest() == null)
                return new ResponseEvent<ProductStatusDTO>().badRequest("event.request is null.");
            final String id = requestEvent.getRequest();
            Long idL = Long.valueOf(id);
            ProductStatus productStatus = repository.findByIdent(idL);
            ProductStatusDTO ProductStatusDTO = ProductStatusParser.setProductStatusDTO(productStatus);
            return new ResponseEvent<ProductStatusDTO>().ok("Success", ProductStatusDTO);
        } catch (Exception ex) {
            log.error("method: read({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<ProductStatusDTO>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<ProductStatusDTO> create(CommandEvent<ProductStatusDTO> requestEvent) {
        log.info("method: create({})", requestEvent);
        try {
            if (requestEvent == null) {
                return new ResponseEvent<ProductStatusDTO>().badRequest("event is null.");
            }
            ProductStatusDTO productStatus = requestEvent.getRequest();
            
            ProductStatus entity = ProductStatusParser.setProductStatus(productStatus);
            repository.save(entity);
            repository.flush();
            productStatus.setId(entity.getId().toString());
            return new ResponseEvent<ProductStatusDTO>().ok("Success", productStatus);
        } catch (Exception ex) {
            log.error("method: create({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<ProductStatusDTO>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<ProductStatusDTO> update(CommandEvent<ProductStatusDTO> requestEvent, String id) {
        log.info("method: update ({},{})", requestEvent, id);
        try {
            if (requestEvent != null) {
                ProductStatusDTO productStatus = requestEvent.getRequest();
                ProductStatus productStatusToUpdate = repository.findByIdent(Long.valueOf(id));

                ProductStatusParser.setProductStatus(productStatusToUpdate, productStatus);
                repository.save(productStatusToUpdate);
                return new ResponseEvent<ProductStatusDTO>().ok("Success", productStatus);
            } else {
                return new ResponseEvent<ProductStatusDTO>().badRequest("event is null.");
            }
        } catch (Exception ex) {
            log.error("method: update({},{})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<ProductStatusDTO>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<String> delete(CommandEvent<String> requestEvent) {
        log.info("method: delete ({})", requestEvent);
        try {
            if (requestEvent != null) {
                final String productId = requestEvent.getRequest();
                Long ident = Long.valueOf(productId);
                ProductStatus productStatusToDelete = repository.findByIdent(ident);
                repository.delete(productStatusToDelete);
                return new ResponseEvent<String>().ok("Success", productId);
            } else {
                return new ResponseEvent<String>().badRequest("event is null.");
            }
        } catch (Exception ex) {
            log.error("method: delete({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<String>().conflict(ex.getMessage());
        }
    }

}
