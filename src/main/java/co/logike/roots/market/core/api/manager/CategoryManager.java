/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.api.manager;

import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.objects.CategoryDTO;
import co.logike.roots.market.core.app.entity.Category;


import java.util.List;

/**
 * Interface manager for {@link Category}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-05
 * @since 1.0
 */
public interface CategoryManager {

  ResponseEvent<List<CategoryDTO>> readAll();

  ResponseEvent<CategoryDTO> read(QueryPKEvent<String> requestEvent);

}
