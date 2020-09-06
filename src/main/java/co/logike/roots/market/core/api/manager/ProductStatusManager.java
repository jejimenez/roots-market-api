/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.api.manager;

import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.objects.ProductStatusDTO;
import co.logike.roots.market.core.app.entity.Product;


import java.util.List;

/**
 * Interface manager for {@link Product}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-05
 * @since 1.0
 */
public interface ProductStatusManager {

  ResponseEvent<List<ProductStatusDTO>> readAll();

  ResponseEvent<ProductStatusDTO> read(QueryPKEvent<String> requestEvent);

  ResponseEvent<ProductStatusDTO> create(CommandEvent<ProductStatusDTO> requestEvent);

  ResponseEvent<ProductStatusDTO> update(CommandEvent<ProductStatusDTO> requestEvent, String id);

  ResponseEvent<String> delete(CommandEvent<String> requestEvent);
}
