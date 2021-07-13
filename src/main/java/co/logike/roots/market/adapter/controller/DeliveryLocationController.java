/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.adapter.controller;

import co.logike.roots.market.adapter.parser.ResponseEntityUtility;
import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.manager.DeliveryLocationManager;
import co.logike.roots.market.core.api.objects.DeliveryLocationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller for the DeliveryLocation.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/market/v1/delivery_location")
@Tag(name = "DeliveryLocation", description = "Delivery Location API")
public class DeliveryLocationController {

    private final DeliveryLocationManager manager;

    @Autowired
    public DeliveryLocationController(DeliveryLocationManager manager) {
        this.manager = manager;
    }

    @GetMapping
    @ResponseBody
    @Operation(summary = "Return all delivery location")
    public ResponseEntity<ResponseEvent<List<DeliveryLocationDTO>>> readAll() {
        log.debug("method: readAll()");
        final ResponseEvent<List<DeliveryLocationDTO>> responseEvent = manager.readAll();
        log.debug("method: readAll() -> {}", responseEvent.getMessage());
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @GetMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Return delivery Location by id")
    public ResponseEntity<ResponseEvent<DeliveryLocationDTO>> read(@PathVariable("id") String id) {
        log.debug("method: read({})", id);
        QueryPKEvent<String> readEvent = new QueryPKEvent<>();
        readEvent.setRequest(id);
        final ResponseEvent<DeliveryLocationDTO> responseEvent = manager.read(readEvent);
        log.debug("method: read({}) -> {}", id, responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    
    @GetMapping("/organization/{id}")
    @ResponseBody
    @Operation(summary = "Return delivery location by organization")
    public ResponseEntity<ResponseEvent<List<DeliveryLocationDTO>>> findByOrganization(@PathVariable("id") String id) {
        log.debug("method: readLast({})");
        QueryPKEvent<String> readEvent = new QueryPKEvent<>();
        readEvent.setRequest(id);
        final ResponseEvent<List<DeliveryLocationDTO>>responseEvent = manager.findByOrganization(readEvent);
        log.debug("method: read({}) -> {}", responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

}
