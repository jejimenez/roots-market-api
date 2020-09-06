/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.adapter.controller;

import co.logike.roots.market.adapter.parser.ResponseEntityUtility;
import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.manager.ProductStatusManager;
import co.logike.roots.market.core.api.objects.ProductStatusDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller for the Product Status.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-05
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/market/v1/product-status")
@Tag(name = "product-status", description = "Product Status API")
public class ProductStatusController {

    private final ProductStatusManager manager;

    @Autowired
    public ProductStatusController(ProductStatusManager manager) {
        this.manager = manager;
    }

    @GetMapping
    @ResponseBody
    @Operation(summary = "Return all products")
    public ResponseEntity<ResponseEvent<List<ProductStatusDTO>>> readAll() {
        log.debug("method: readAll()");
        final ResponseEvent<List<ProductStatusDTO>> responseEvent = manager.readAll();
        log.debug("method: readAll() -> {}", responseEvent.getMessage());
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @GetMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Return product status by id")
    public ResponseEntity<ResponseEvent<ProductStatusDTO>> read(@PathVariable("id") String id) {
        log.debug("method: read({})", id);
        QueryPKEvent<String> readEvent = new QueryPKEvent<>();
        readEvent.setRequest(id);
        final ResponseEvent<ProductStatusDTO> responseEvent = manager.read(readEvent);
        log.debug("method: read({}) -> {}", id, responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @PostMapping
    @ResponseBody
    @Operation(summary = "Create product status")
    public ResponseEntity<ResponseEvent<ProductStatusDTO>> create(@RequestBody ProductStatusDTO domain) {
        log.debug("method: create({})", domain);
        final CommandEvent<ProductStatusDTO> requestEvent = new CommandEvent<>();
        requestEvent.setRequest(domain);
        final ResponseEvent<ProductStatusDTO> responseEvent = manager.create(requestEvent);
        log.debug("method: create({}) -> {}", domain, responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Update product status by id")
    public ResponseEntity<ResponseEvent<ProductStatusDTO>> update(@PathVariable("id") String id, @RequestBody ProductStatusDTO domain) {
        log.debug("method: update id: {}, ({})", id, domain);
        final CommandEvent<ProductStatusDTO> requestEvent = new CommandEvent<>();
        requestEvent.setRequest(domain);
        final ResponseEvent<ProductStatusDTO> responseEvent = manager.update(requestEvent, id);
        log.debug("method: update({}) -> {}", domain, responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Delete product by id")
    public ResponseEntity<ResponseEvent<String>> delete(@PathVariable("id") String id) {
        log.debug("method: deleteDomain({})", id);
        CommandEvent<String> commandEvent = new CommandEvent<>();
        commandEvent.setRequest(id);
        final ResponseEvent<String> responseEvent = manager.delete(commandEvent);
        log.debug("method: deleteDomain({}) -> {}", id, responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }
}
