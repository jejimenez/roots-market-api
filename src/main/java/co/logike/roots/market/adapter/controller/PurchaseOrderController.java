/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.adapter.controller;

import co.logike.roots.market.adapter.parser.ResponseEntityUtility;
import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.manager.OrderProductManager;
import co.logike.roots.market.core.api.manager.ProductManager;
import co.logike.roots.market.core.api.manager.PurchaseOrderManager;
import co.logike.roots.market.core.api.objects.OrderProductDTO;
import co.logike.roots.market.core.api.objects.ProductDTO;
import co.logike.roots.market.core.api.objects.PurchaseOrderDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    public PurchaseOrderController(PurchaseOrderManager manager, ProductManager managerProduct, OrderProductManager managerOrderProduct) {
        this.manager = manager;
        this.managerProduct = managerProduct;
        this.managerOrderProduct = managerOrderProduct;
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
		        final ResponseEvent<List<OrderProductDTO>> responseEventProducts = managerOrderProduct.createList(responseEvent.getData().getId(), requestEventProducts);
		        log.debug("method: create({}) -> {}", domain, responseEventProducts);
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
}
