/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.adapter.controller;

import co.logike.roots.market.adapter.parser.ResponseEntityUtility;
import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.manager.ProductManager;
import co.logike.roots.market.core.api.objects.ProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller for the Product.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/market/v1/product")
@Tag(name = "product", description = "Product API")
public class ProductController {

    private final ProductManager manager;

    @Autowired
    public ProductController(ProductManager manager) {
        this.manager = manager;
    }

    @GetMapping()
    @ResponseBody
    @Operation(summary = "Return all products")
    public ResponseEntity<ResponseEvent<List<ProductDTO>>> readAll() {
        log.debug("method: readAll()");
        final ResponseEvent<List<ProductDTO>> responseEvent = manager.readAll();
        log.debug("method: readAll(orderby) -> {}", responseEvent.getMessage());
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @GetMapping(params="orderby")
    @ResponseBody
    @Operation(summary = "Return all products with order")
    public ResponseEntity<ResponseEvent<List<ProductDTO>>> readAllOrderBy(@RequestParam("orderby") String orderBy) {
        log.debug("method: readAll()");
        final ResponseEvent<List<ProductDTO>> responseEvent = manager.readNotDeletedOrderBy(orderBy);
        log.debug("method: readAll(orderby) -> {}", responseEvent.getMessage());
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @GetMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Return product by id")
    public ResponseEntity<ResponseEvent<ProductDTO>> read(@PathVariable("id") String id) {
        log.debug("method: read({})", id);
        QueryPKEvent<String> readEvent = new QueryPKEvent<>();
        readEvent.setRequest(id);
        final ResponseEvent<ProductDTO> responseEvent = manager.read(readEvent);
        log.debug("method: read({}) -> {}", id, responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @PostMapping
    @ResponseBody
    @Operation(summary = "Create product")
    public ResponseEntity<ResponseEvent<ProductDTO>> create(@RequestBody ProductDTO domain) {
        log.debug("method: create({})", domain);
        final CommandEvent<ProductDTO> requestEvent = new CommandEvent<>();
        requestEvent.setRequest(domain);
        final ResponseEvent<ProductDTO> responseEvent = manager.create(requestEvent);
        log.debug("method: create({}) -> {}", domain, responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Update product by id")
    public ResponseEntity<ResponseEvent<ProductDTO>> update(@PathVariable("id") String id, @RequestBody ProductDTO domain) {
        log.debug("method: update id: {}, ({})", id, domain);
        final CommandEvent<ProductDTO> requestEvent = new CommandEvent<>();
        requestEvent.setRequest(domain);
        final ResponseEvent<ProductDTO> responseEvent = manager.update(requestEvent, id);
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
