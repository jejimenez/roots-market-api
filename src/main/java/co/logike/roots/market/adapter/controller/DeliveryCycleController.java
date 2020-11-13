/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.adapter.controller;

import co.logike.roots.market.adapter.parser.ResponseEntityUtility;
import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.manager.DeliveryCycleManager;
import co.logike.roots.market.core.api.objects.DeliveryCycleDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller for the DeliveryCycle.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/market/v1/delivery_cycle")
@Tag(name = "deliveryCycle", description = "Delivery Cycle API")
public class DeliveryCycleController {

    private final DeliveryCycleManager manager;

    @Autowired
    public DeliveryCycleController(DeliveryCycleManager manager) {
        this.manager = manager;
    }

    @GetMapping
    @ResponseBody
    @Operation(summary = "Return all products")
    public ResponseEntity<ResponseEvent<List<DeliveryCycleDTO>>> readAll() {
        log.debug("method: readAll()");
        final ResponseEvent<List<DeliveryCycleDTO>> responseEvent = manager.readAll();
        log.debug("method: readAll() -> {}", responseEvent.getMessage());
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @GetMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Return product by id")
    public ResponseEntity<ResponseEvent<DeliveryCycleDTO>> read(@PathVariable("id") String id) {
        log.debug("method: read({})", id);
        QueryPKEvent<String> readEvent = new QueryPKEvent<>();
        readEvent.setRequest(id);
        final ResponseEvent<DeliveryCycleDTO> responseEvent = manager.read(readEvent);
        log.debug("method: read({}) -> {}", id, responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

}
