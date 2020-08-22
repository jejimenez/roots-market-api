/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.api.manager;

import co.logike.roots.market.core.api.events.QueryEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.objects.ProductDTO;
import co.logike.roots.market.core.app.entity.Product;


import java.util.List;

/**
 * Interface manager for {@link Product}.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
public interface ProductManager {

  ResponseEvent<List<ProductDTO>> read(QueryEvent requestEvent);
//
//  ResponseEvent<ProductDTO> read(QueryPKEvent<String> requestEvent);
//
//  ResponseEvent<ProductDTO> create(CommandEvent<ProductDTO> requestEvent);
//
//  ResponseEvent<ProductDTO> update(CommandEvent<ProductDTO> requestEvent, String id);
}
