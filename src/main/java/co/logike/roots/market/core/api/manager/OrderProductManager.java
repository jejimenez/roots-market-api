/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.api.manager;

import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.objects.OrderProductDTO;


import java.util.List;

/**
 * Interface manager for {@link OrderProduct}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jiménez</a>
 * @version 1.0 2020-09-19
 * @since 1.0
 */
public interface OrderProductManager {

  ResponseEvent<List<OrderProductDTO>> readAll();

  ResponseEvent<OrderProductDTO> read(QueryPKEvent<String> requestEvent);

  ResponseEvent<OrderProductDTO> create(CommandEvent<OrderProductDTO> requestEvent);
  
  ResponseEvent<List<OrderProductDTO>> createList(String idPurchaseOrder, CommandEvent<List<OrderProductDTO>> requestEvent);

  ResponseEvent<OrderProductDTO> update(CommandEvent<OrderProductDTO> requestEvent, String id);

  ResponseEvent<String> delete(CommandEvent<String> requestEvent);
}
