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
import co.logike.roots.market.core.api.objects.OrderProductMailedDTO;
import co.logike.roots.market.core.api.objects.ProductDTO;
import co.logike.roots.market.core.app.entity.*;
import co.logike.roots.market.core.app.parser.OrderProductParser;
import co.logike.roots.market.core.app.parser.ProductParser;
import co.logike.roots.market.core.app.repository.*;
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
    private final OrderReportRepository orderReportRepository;
    private final OrderGroupingProducerReportRepository orderGroupingProducerReportRepository;
    

    @Autowired
    public OrderProductManagerHandler(OrderProductRepository repository, ProductRepository productRepository,
    		PurchaseOrderRepository purchaseOrderRepository, ProductStatusRepository productStatusRepository,OrderReportRepository orderReportRepository,
    		OrderGroupingProducerReportRepository orderGroupingProducerReportRepository ) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.productStatusRepository = productStatusRepository;
        this.orderReportRepository = orderReportRepository;
        this.orderGroupingProducerReportRepository = orderGroupingProducerReportRepository;
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
    public ResponseEvent<List<OrderProductDTO>> createList(String idPurchaseOrder, CommandEvent<List<OrderProductDTO>> requestEvent) {
        log.info("method: create({})", requestEvent);
        try {
            if (requestEvent == null) {
                return new ResponseEvent<List<OrderProductDTO>>().badRequest("event is null.");
            }
            if (idPurchaseOrder == null) {
                return new ResponseEvent<List<OrderProductDTO>>().badRequest("Id PurchaseOrder is null.");
            }
            List<OrderProductDTO> orderProducts = requestEvent.getRequest();
            List<OrderProductDTO> finalOrderProducts = new  ArrayList<OrderProductDTO>();
            List<OrderProduct> entityOrderProds = new ArrayList();
            
            for(OrderProductDTO orderProduct : orderProducts) {
            	orderProduct.setPurchaseOrder(idPurchaseOrder);
	            Product product = productRepository.findByIdent(Long.valueOf(orderProduct.getProduct()));
	            PurchaseOrder purchaseOrder = purchaseOrderRepository.findByIdent(Long.valueOf(orderProduct.getPurchaseOrder()));
	            ProductStatus productStatus = productStatusRepository.findByIdent(Long.valueOf(orderProduct.getProductStatus()));
	            
	            OrderProduct entity = OrderProductParser.setOrderProduct(orderProduct, purchaseOrder, product, productStatus);
	            OrderProductDTO orderProductDTO = OrderProductParser.setOrderProductDTO(entity);
	            //OrderProductMailedDTO orderProdMailedDTO = OrderProductParser.setOrderProductDTO(entity, product);
	            finalOrderProducts.add(orderProductDTO);
	            entityOrderProds.add(entity);
	            //ProductDTO productDTO = ProductParser.setProductDTO(product);
            }
            
            repository.saveAll(entityOrderProds);
            repository.flush();
            return new ResponseEvent<List<OrderProductDTO>>().ok("Success", finalOrderProducts);
        } catch (Exception ex) {
            log.error("method: create({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<List<OrderProductDTO>>().conflict(ex.getMessage());
        }
    }
    


    @Override
    public ResponseEvent<List<OrderProductMailedDTO>> createListMailed(String idPurchaseOrder, CommandEvent<List<OrderProductDTO>> requestEvent) {
        log.info("method: create({})", requestEvent);
        try {
            if (requestEvent == null) {
                return new ResponseEvent<List<OrderProductMailedDTO>>().badRequest("event is null.");
            }
            if (idPurchaseOrder == null) {
                return new ResponseEvent<List<OrderProductMailedDTO>>().badRequest("Id PurchaseOrder is null.");
            }
            List<OrderProductDTO> orderProducts = requestEvent.getRequest();
            List<OrderProductMailedDTO> finalOrderProducts = new  ArrayList<OrderProductMailedDTO>();
            List<OrderProduct> entityOrderProds = new ArrayList();
            
            for(OrderProductDTO orderProduct : orderProducts) {
            	orderProduct.setPurchaseOrder(idPurchaseOrder);
	            Product product = productRepository.findByIdent(Long.valueOf(orderProduct.getProduct()));
	            PurchaseOrder purchaseOrder = purchaseOrderRepository.findByIdent(Long.valueOf(orderProduct.getPurchaseOrder()));
	            ProductStatus productStatus = productStatusRepository.findByIdent(Long.valueOf(orderProduct.getProductStatus()));
	            
	            OrderProduct entity = OrderProductParser.setOrderProduct(orderProduct, purchaseOrder, product, productStatus);
	            //OrderProductDTO orderProductDTO = OrderProductParser.setOrderProductDTO(entity);
	            OrderProductMailedDTO orderProdMailedDTO = OrderProductParser.setOrderProductDTO(entity, product);
	            finalOrderProducts.add(orderProdMailedDTO);
	            entityOrderProds.add(entity);
	            //ProductDTO productDTO = ProductParser.setProductDTO(product);
            }
            
            repository.saveAll(entityOrderProds);
            repository.flush();
            return new ResponseEvent<List<OrderProductMailedDTO>>().ok("Success", finalOrderProducts);
        } catch (Exception ex) {
            log.error("method: create({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<List<OrderProductMailedDTO>>().conflict(ex.getMessage());
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

    @Override
    public ResponseEvent<List<OrderReport>> getOrders(String sDate,String eDate) {
        log.info("method: read({})", sDate+eDate);
        try {
//            if (requestEvent == null)
//                return new ResponseEvent<List<OrderReport>>().badRequest("event is null.");
//            if (requestEvent.getRequest() == null)
//                return new ResponseEvent<List<OrderReport>>().badRequest("event.request is null.");
            //final String id = requestEvent.getRequest();
            //Long idL = Long.valueOf(id);
            List<OrderReport> orderProductDTO = orderReportRepository.findOrders(sDate,eDate);
            //OrderProductDTO orderProductDTO = OrderProductParser.setOrderProductDTO(null);
            return new ResponseEvent<List<OrderReport>>().ok("Success", orderProductDTO);
        } catch (Exception ex) {
            log.error("method: read({}, {})", sDate, ex.getMessage(), ex);
            return new ResponseEvent<List<OrderReport>>().conflict(ex.getMessage());
        }
    }
    
    @Override
    public ResponseEvent<List<OrderGroupingProducerReport>> getOrdersGroupingProducer(String sDate,String eDate) {
        log.info("method: read({})", sDate+eDate);
        try {
//            if (requestEvent == null)
//                return new ResponseEvent<List<OrderReport>>().badRequest("event is null.");
//            if (requestEvent.getRequest() == null)
//                return new ResponseEvent<List<OrderReport>>().badRequest("event.request is null.");
            //final String id = requestEvent.getRequest();
            //Long idL = Long.valueOf(id);
            List<OrderGroupingProducerReport> orderProductDTO = orderGroupingProducerReportRepository.findOrdersGroupingByProducer(sDate,eDate);
            //OrderProductDTO orderProductDTO = OrderProductParser.setOrderProductDTO(null);
            return new ResponseEvent<List<OrderGroupingProducerReport>>().ok("Success", orderProductDTO);
        } catch (Exception ex) {
            log.error("method: read({}, {})", sDate, ex.getMessage(), ex);
            return new ResponseEvent<List<OrderGroupingProducerReport>>().conflict(ex.getMessage());
        }
    }
}
