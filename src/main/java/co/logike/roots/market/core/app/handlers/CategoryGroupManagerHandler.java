/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.handlers;

import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.manager.CategoryGroupManager;
import co.logike.roots.market.core.api.objects.CategoryGroupDTO;
import co.logike.roots.market.core.app.entity.CategoryGroup;
import co.logike.roots.market.core.app.parser.CategoryGroupParser;
import co.logike.roots.market.core.app.repository.CategoryGroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler for {@link CategoryGroupManager}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Service
@Slf4j
public class CategoryGroupManagerHandler implements CategoryGroupManager {

    private final CategoryGroupRepository repository;
    
    @Autowired
    public CategoryGroupManagerHandler(CategoryGroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEvent<List<CategoryGroupDTO>> readAll() {
        log.debug("method: readAll()");
        try {
            List<CategoryGroupDTO> finalList = new ArrayList<>();
            List<CategoryGroup> categoryGrouptList = repository.findAllByNotDeleted();
            for (CategoryGroup category : categoryGrouptList) {
            	CategoryGroupDTO CategoryDTO = CategoryGroupParser.setCategoryGroupDTO(category);
                finalList.add(CategoryDTO);
            }
            return new ResponseEvent<List<CategoryGroupDTO>>().ok("Success", finalList);
        } catch (Exception ex) {
            log.error("method: read({}, {})", ex.getMessage(), ex);
            return new ResponseEvent<List<CategoryGroupDTO>>().conflict(ex.getMessage());
        }
    }

    @Override
    public ResponseEvent<CategoryGroupDTO> read(QueryPKEvent<String> requestEvent) {
        log.info("method: read({})", requestEvent);
        try {
            if (requestEvent == null)
                return new ResponseEvent<CategoryGroupDTO>().badRequest("event is null.");
            if (requestEvent.getRequest() == null)
                return new ResponseEvent<CategoryGroupDTO>().badRequest("event.request is null.");
            final String id = requestEvent.getRequest();
            Long idL = Long.valueOf(id);
            CategoryGroup category = repository.findByIdent(idL);
            CategoryGroupDTO CategoryGroupDTO = CategoryGroupParser.setCategoryGroupDTO(category);
            return new ResponseEvent<CategoryGroupDTO>().ok("Success", CategoryGroupDTO);
        } catch (Exception ex) {
            log.error("method: read({}, {})", requestEvent, ex.getMessage(), ex);
            return new ResponseEvent<CategoryGroupDTO>().conflict(ex.getMessage());
        }
    }
}
