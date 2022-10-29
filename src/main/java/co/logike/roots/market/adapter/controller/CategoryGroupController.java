/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.adapter.controller;

import co.logike.roots.market.adapter.parser.ResponseEntityUtility;
import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.manager.CategoryGroupManager;
import co.logike.roots.market.core.api.manager.CategoryManager;
import co.logike.roots.market.core.api.objects.CategoryDTO;
import co.logike.roots.market.core.api.objects.CategoryGroupDTO;
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
@RequestMapping("/market/v1/category_group")
@Tag(name = "categoryGroup", description = "Category Group API")
public class CategoryGroupController {

	private final CategoryGroupManager manager;

    @Autowired
    public CategoryGroupController(CategoryGroupManager manager) {
        this.manager = manager;
    }

    @GetMapping
    @ResponseBody
    @Operation(summary = "Return all category groups not inactive")
    public ResponseEntity<ResponseEvent<List<CategoryGroupDTO>>> readAll() {
        log.debug("method: readAll()");
        final ResponseEvent<List<CategoryGroupDTO>> responseEvent = manager.readAll();
        log.debug("method: readAll() -> {}", responseEvent.getMessage());
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

    @GetMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Return category groups by id")
    public ResponseEntity<ResponseEvent<CategoryGroupDTO>> read(@PathVariable("id") String id) {
        log.debug("method: read({})", id);
        QueryPKEvent<String> readEvent = new QueryPKEvent<>();
        readEvent.setRequest(id);
        final ResponseEvent<CategoryGroupDTO> responseEvent = manager.read(readEvent);
        log.debug("method: read({}) -> {}", id, responseEvent);
        return ResponseEntityUtility.buildHttpResponse(responseEvent);
    }

}
