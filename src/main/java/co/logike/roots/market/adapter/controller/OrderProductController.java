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
import co.logike.roots.market.core.api.objects.OrderProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller for the OrderProduct.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/market/v1/order_product")
@Tag(name = "orderProduct", description = "OrderProduct API")
public class OrderProductController {

    private final OrderProductManager manager;

    @Autowired
    public OrderProductController(OrderProductManager manager) {
        this.manager = manager;
    }

    @GetMapping
    @ResponseBody
    @Operation(summary = "Return all orderProducts")
    public ResponseEntity<ResponseEvent<List<OrderProductDTO>>> readAll() {
        log.debug("method: readAll()");
        final ResponseEvent<List<OrderProductDTO>> responseEvent = manager.readAll();
        log.debug("method: readAll() -> {}", responseEvent.getMessage());
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @GetMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Return orderProduct by id")
    public ResponseEntity<ResponseEvent<OrderProductDTO>> read(@PathVariable("id") String id) {
        log.debug("method: read({})", id);
        QueryPKEvent<String> readEvent = new QueryPKEvent<>();
        readEvent.setRequest(id);
        final ResponseEvent<OrderProductDTO> responseEvent = manager.read(readEvent);
        log.debug("method: read({}) -> {}", id, responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @PostMapping
    @ResponseBody
    @Operation(summary = "Create orderProduct")
    public ResponseEntity<ResponseEvent<OrderProductDTO>> create(@RequestBody OrderProductDTO domain) {
        log.debug("method: create({})", domain);
        final CommandEvent<OrderProductDTO> requestEvent = new CommandEvent<>();
        requestEvent.setRequest(domain);
        final ResponseEvent<OrderProductDTO> responseEvent = manager.create(requestEvent);
        log.debug("method: create({}) -> {}", domain, responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Update orderProduct by id")
    public ResponseEntity<ResponseEvent<OrderProductDTO>> update(@PathVariable("id") String id, @RequestBody OrderProductDTO domain) {
        log.debug("method: update id: {}, ({})", id, domain);
        final CommandEvent<OrderProductDTO> requestEvent = new CommandEvent<>();
        requestEvent.setRequest(domain);
        final ResponseEvent<OrderProductDTO> responseEvent = manager.update(requestEvent, id);
        log.debug("method: update({}) -> {}", domain, responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Delete orderProduct by id")
    public ResponseEntity<ResponseEvent<String>> delete(@PathVariable("id") String id) {
        log.debug("method: deleteDomain({})", id);
        CommandEvent<String> commandEvent = new CommandEvent<>();
        commandEvent.setRequest(id);
        final ResponseEvent<String> responseEvent = manager.delete(commandEvent);
        log.debug("method: deleteDomain({}) -> {}", id, responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }
}
