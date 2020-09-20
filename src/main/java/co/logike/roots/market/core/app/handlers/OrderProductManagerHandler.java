/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.handlers;

import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.manager.OrderProductManager;
import co.logike.roots.market.core.api.objects.OrderProductDTO;
import co.logike.roots.market.core.app.entity.Product;
import co.logike.roots.market.core.app.entity.ProductStatus;
import co.logike.roots.market.core.app.entity.PurchaseOrder;
import co.logike.roots.market.core.app.entity.OrderProduct;
import co.logike.roots.market.core.app.parser.OrderProductParser;
import co.logike.roots.market.core.app.repository.ProductRepository;
import co.logike.roots.market.core.app.repository.ProductStatusRepository;
import co.logike.roots.market.core.app.repository.PurchaseOrderRepository;
import co.logike.roots.market.core.app.repository.OrderProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler for {@link OrderProductManager}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jiménez</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Service
@Slf4j
public class OrderProductManagerHandler implements OrderProductManager {

    private final OrderProductRepository repository;
    private final ProductRepository productRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductStatusRepository productStatusRepository;

    @Autowired
    public OrderProductManagerHandler(OrderProductRepository repository, ProductRepository productRepository,
    		PurchaseOrderRepository purchaseOrderRepository, ProductStatusRepository productStatusRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.productStatusRepository = productStatusRepository;
    }

    @Override
    public ResponseEvent<List<OrderProductDTO>> readAll() {
        log.debug("method: readAll()");
        try {
            List<OrderProductDTO> finalList = new ArrayList<>();
            List<OrderProduct> orderProductList = repository.findAll();
            for (OrderProduct orderProduct : orderProductList) {
            	OrderProductDTO orderProductDTO = OrderProductParser.setOrderProductDTO(orderProduct);
                finalList.add(orderProductDTO);
            }
            return new ResponseEvent<List<OrderProductDTO>>().ok("Success", finalList);
        } catch (Exception ex) {
            log.error("method: read({}, {})", ex.getMessage(), ex);
            return new ResponseEvent<List<OrderProductDTO>>().conflict(ex.getMessage());
        }
    }


    @Override
    public ResponseEvent<OrderProductDTO> read(QueryPKEvent<String> requestEvent) {
        log.info("method: read({})", requestEvent);
        try {
            if (requestEvent == null)
                return new ResponseEvent<OrderProductDTO>().badRequest("event is null.");
            if (requestEvent.getRequest() == null)
                return new ResponseEvent<OrderProductDTO>().badRequest("event.request is null.");
            final String id = requestEvent.getRequest();
            Long idL = Long.valueOf(id);
            OrderProduct orderProduct = repository.findByIdent(idL);
            OrderProductDTO orderProductDTO = OrderProductParser.setOrderProductDTO(orderProduct);
            return new ResponseEvent<OrderProductDTO>().ok("Success", orderProductDTO);
        } catch (Exception ex) {
            log.error("method: read({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<OrderProductDTO>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<OrderProductDTO> create(CommandEvent<OrderProductDTO> requestEvent) {
        log.info("method: create({})", requestEvent);
        try {
            if (requestEvent == null) {
                return new ResponseEvent<OrderProductDTO>().badRequest("event is null.");
            }
            OrderProductDTO orderProduct = requestEvent.getRequest();
            Product product = productRepository.findByIdent(Long.valueOf(orderProduct.getProduct()));
            PurchaseOrder purchaseOrder = purchaseOrderRepository.findByIdent(Long.valueOf(orderProduct.getPurchaseOrder()));
            ProductStatus productStatus = productStatusRepository.findByIdent(Long.valueOf(orderProduct.getProductStatus()));
             
            
            OrderProduct entity = OrderProductParser.setOrderProduct(orderProduct, purchaseOrder, product, productStatus);
            repository.save(entity);
            repository.flush();
            orderProduct.setId(entity.getId().toString());
            return new ResponseEvent<OrderProductDTO>().ok("Success", orderProduct);
        } catch (Exception ex) {
            log.error("method: create({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<OrderProductDTO>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<OrderProductDTO> update(CommandEvent<OrderProductDTO> requestEvent, String id) {
        log.info("method: update ({},{})", requestEvent, id);
        try {
            if (requestEvent != null) {
                OrderProductDTO orderProduct = requestEvent.getRequest();
                OrderProduct orderProductToUpdate = repository.findByIdent(Long.valueOf(id));
                Product product = productRepository.findByIdent(Long.valueOf(orderProduct.getProduct()));
                PurchaseOrder purchaseOrder = purchaseOrderRepository.findByIdent(Long.valueOf(orderProduct.getPurchaseOrder()));
                ProductStatus productStatus = productStatusRepository.findByIdent(Long.valueOf(orderProduct.getProductStatus()));

                OrderProductParser.setOrderProduct(orderProductToUpdate, orderProduct, purchaseOrder, product, productStatus);
                repository.save(orderProductToUpdate);
                return new ResponseEvent<OrderProductDTO>().ok("Success", orderProduct);
            } else {
                return new ResponseEvent<OrderProductDTO>().badRequest("event is null.");
            }
        } catch (Exception ex) {
            log.error("method: update({},{})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<OrderProductDTO>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<String> delete(CommandEvent<String> requestEvent) {
        log.info("method: delete ({})", requestEvent);
        try {
            if (requestEvent != null) {
                final String orderProductId = requestEvent.getRequest();
                Long ident = Long.valueOf(orderProductId);
                OrderProduct orderProductToDelete = repository.findByIdent(ident);
                repository.delete(orderProductToDelete);
                return new ResponseEvent<String>().ok("Success", orderProductId);
            } else {
                return new ResponseEvent<String>().badRequest("event is null.");
            }
        } catch (Exception ex) {
            log.error("method: delete({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<String>().conflict(ex.getMessage());
        }
    }

}
