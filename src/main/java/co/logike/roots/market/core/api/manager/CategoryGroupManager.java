/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.api.manager;

import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.objects.CategoryGroupDTO;
import co.logike.roots.market.core.app.entity.CategoryGroup;


import java.util.List;

/**
 * Interface manager for {@link CategoryGroup}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2022-09-05
 * @since 1.0
 */
public interface CategoryGroupManager {

  ResponseEvent<List<CategoryGroupDTO>> readAll();

  ResponseEvent<CategoryGroupDTO> read(QueryPKEvent<String> requestEvent);

}