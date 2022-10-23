/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.adapter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.logike.roots.market.adapter.parser.ResponseEntityUtility;
import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.manager.EmailNotificationManager;
import co.logike.roots.market.core.api.manager.OrderProductManager;
import co.logike.roots.market.core.api.manager.ProductManager;
import co.logike.roots.market.core.api.manager.PurchaseOrderManager;
import co.logike.roots.market.core.api.objects.OrderProductDTO;
import co.logike.roots.market.core.api.objects.OrderProductMailedDTO;
import co.logike.roots.market.core.api.objects.ProductDTO;
import co.logike.roots.market.core.api.objects.PurchaseOrderDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * Rest controller for the PurchaseOrder.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-19
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/market/v1/purchase_order")
@Tag(name = "purchaseOrder", description = "PurchaseOrder API")
public class PurchaseOrderController {

    private final PurchaseOrderManager manager;
    private final ProductManager managerProduct;
    private final OrderProductManager managerOrderProduct;
    private final EmailNotificationManager managerEmailNotification;

    @Value("${spring.mail.username}")
    public String username;
    
    @Value("${spring.mail.password}")
    public String password;
    
    @Autowired
    public PurchaseOrderController(PurchaseOrderManager manager, ProductManager managerProduct, OrderProductManager managerOrderProduct, EmailNotificationManager managerEmailNotification) {
        this.manager = manager;
        this.managerProduct = managerProduct;
        this.managerOrderProduct = managerOrderProduct;
        this.managerEmailNotification = managerEmailNotification;
    }

    @GetMapping
    @ResponseBody
    @Operation(summary = "Return all purchaseOrders")
    public ResponseEntity<ResponseEvent<List<PurchaseOrderDTO>>> readAll() {
        log.debug("method: readAll()");
        final ResponseEvent<List<PurchaseOrderDTO>> responseEvent = manager.readAll();
        log.debug("method: readAll() -> {}", responseEvent.getMessage());
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }


    @GetMapping("/{id}/products")
    @ResponseBody
    @Operation(summary = "Return all products by purchase order")
    public ResponseEntity<ResponseEvent<List<ProductDTO>>> readByPurchaseOrder(@PathVariable("id") String id) {
        log.debug("method: readByPurchaseOrder({})", id);
        QueryPKEvent<String> readEvent = new QueryPKEvent<>();
        readEvent.setRequest(id);
        final ResponseEvent<List<ProductDTO>> responseEvent = managerProduct.readByPurchaseOrder(readEvent);
        log.debug("method: readByPurchaseOrder() -> {}", responseEvent.getMessage());
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }


    @GetMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Return purchaseOrder by id")
    public ResponseEntity<ResponseEvent<PurchaseOrderDTO>> read(@PathVariable("id") String id) {
        log.debug("method: read({})", id);
        QueryPKEvent<String> readEvent = new QueryPKEvent<>();
        readEvent.setRequest(id);
        final ResponseEvent<PurchaseOrderDTO> responseEvent = manager.read(readEvent);
        log.debug("method: read({}) -> {}", id, responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @PostMapping
    @ResponseBody
    @Operation(summary = "Create purchaseOrder")
    public ResponseEntity<ResponseEvent<PurchaseOrderDTO>> create(@RequestBody PurchaseOrderDTO domain) {
        log.debug("method: create({})", domain);
        final CommandEvent<PurchaseOrderDTO> requestEvent = new CommandEvent<>();
        requestEvent.setRequest(domain);
        final ResponseEvent<PurchaseOrderDTO> responseEvent = manager.create(requestEvent);
        log.debug("method: create({}) -> {}", domain, responseEvent);
        //
        if(responseEvent.getData() != null && responseEvent.getData().getId() != null) {
	        if(domain.getOrderProducts() != null && domain.getOrderProducts().size() > 0 ) {
		        final CommandEvent<List<OrderProductDTO>> requestEventProducts = new CommandEvent<>();
		        requestEventProducts.setRequest(domain.getOrderProducts());
		        final ResponseEvent<List<OrderProductMailedDTO>> responseEventProducts = managerOrderProduct.createListMailed(responseEvent.getData().getId(), requestEventProducts);
		        log.debug("method: create({}) -> {}", domain, responseEventProducts);
		        //Send Notification Email
		        managerEmailNotification.sendNotificationNewPurchase(responseEvent, responseEventProducts);
	        }
        }
        //
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }


    @PostMapping("/{id}/productos/")
    @ResponseBody
    @Operation(summary = "Create orderProducts of Purchase Order")
    public ResponseEntity<ResponseEvent< List<OrderProductDTO>>> createOrderProducts(@PathVariable("id") String id, @RequestBody List<OrderProductDTO> domain) {
        log.debug("method: create({})", domain);
        final CommandEvent<List<OrderProductDTO>> requestEvent = new CommandEvent<>();
        requestEvent.setRequest(domain);
        final ResponseEvent<List<OrderProductDTO>> responseEvent = managerOrderProduct.createList(id, requestEvent);
        log.debug("method: create({}) -> {}", domain, responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Update purchaseOrder by id")
    public ResponseEntity<ResponseEvent<PurchaseOrderDTO>> update(@PathVariable("id") String id, @RequestBody PurchaseOrderDTO domain) {
        log.debug("method: update id: {}, ({})", id, domain);
        final CommandEvent<PurchaseOrderDTO> requestEvent = new CommandEvent<>();
        requestEvent.setRequest(domain);
        final ResponseEvent<PurchaseOrderDTO> responseEvent = manager.update(requestEvent, id);
        log.debug("method: update({}) -> {}", domain, responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Delete purchaseOrder by id")
    public ResponseEntity<ResponseEvent<String>> delete(@PathVariable("id") String id) {
        log.debug("method: deleteDomain({})", id);
        CommandEvent<String> commandEvent = new CommandEvent<>();
        commandEvent.setRequest(id);
        final ResponseEvent<String> responseEvent = manager.delete(commandEvent);
        log.debug("method: deleteDomain({}) -> {}", id, responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @PostMapping("/testsmpt")
    @ResponseBody
    @Operation(summary = "Test SMTP")
    public ResponseEntity<ResponseEvent<Boolean>> testSMPT() {
        log.debug("method: testSMPT({})");
        log.info("Username {} password {}", username, password);
        CommandEvent<String> commandEvent = new CommandEvent<>();
        //Send Notification Email
        final ResponseEvent<Boolean> responseEvent = managerEmailNotification.sendNotificationTest();
        log.debug("method: deleteDomain({}) -> {}", responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }
}
