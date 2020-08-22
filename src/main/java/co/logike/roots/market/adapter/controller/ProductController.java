/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.adapter.controller;

import co.logike.roots.market.adapter.parser.ResponseEntityUtility;
import co.logike.roots.market.core.api.events.QueryEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.manager.ProductManager;
import co.logike.roots.market.core.api.objects.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
public class ProductController {

    private final ProductManager manager;

    @Autowired
    public ProductController(ProductManager manager) {
        this.manager = manager;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<ResponseEvent<List<ProductDTO>>> read(@RequestParam Map<String, String> params) {
        log.debug("method: read({})", params);
        QueryEvent readEvent = new QueryEvent();
        readEvent.setFilters(params);
        final ResponseEvent<List<ProductDTO>> responseEvent = manager.read(readEvent);
        log.debug("method: read({}) -> {}", params, responseEvent.getMessage());
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

}
