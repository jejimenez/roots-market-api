/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.handlers;

import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.manager.CategoryManager;
import co.logike.roots.market.core.api.objects.CategoryDTO;
import co.logike.roots.market.core.app.entity.Category;
import co.logike.roots.market.core.app.parser.CategoryParser;
import co.logike.roots.market.core.app.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler for {@link ProductManager}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Service
@Slf4j
public class CategoryManagerHandler implements CategoryManager {

    private final CategoryRepository repository;
    
    @Autowired
    public CategoryManagerHandler(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEvent<List<CategoryDTO>> readAll() {
        log.debug("method: readAll()");
        try {
            List<CategoryDTO> finalList = new ArrayList<>();
            List<Category> productList = repository.findAll();
            for (Category category : productList) {
                CategoryDTO CategoryDTO = CategoryParser.setCategoryDTO(category);
                finalList.add(CategoryDTO);
            }
            return new ResponseEvent<List<CategoryDTO>>().ok("Success", finalList);
        } catch (Exception ex) {
            log.error("method: read({}, {})", ex.getMessage(), ex);
            return new ResponseEvent<List<CategoryDTO>>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<CategoryDTO> read(QueryPKEvent<String> requestEvent) {
        log.info("method: read({})", requestEvent);
        try {
            if (requestEvent == null)
                return new ResponseEvent<CategoryDTO>().badRequest("event is null.");
            if (requestEvent.getRequest() == null)
                return new ResponseEvent<CategoryDTO>().badRequest("event.request is null.");
            final String id = requestEvent.getRequest();
            Long idL = Long.valueOf(id);
            Category category = repository.findByIdent(idL);
            CategoryDTO CategoryDTO = CategoryParser.setCategoryDTO(category);
            return new ResponseEvent<CategoryDTO>().ok("Success", CategoryDTO);
        } catch (Exception ex) {
            log.error("method: read({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<CategoryDTO>().conflict(ex.getMessage());
        }
    }
}
