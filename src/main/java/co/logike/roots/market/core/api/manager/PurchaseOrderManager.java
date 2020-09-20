/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.api.manager;

import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.objects.ProductDTO;
import co.logike.roots.market.core.api.objects.PurchaseOrderDTO;
import co.logike.roots.market.core.app.entity.Product;


import java.util.List;

/**
 * Interface manager for {@link PurchaseOrder}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jiménez</a>
 * @version 1.0 2020-09-19
 * @since 1.0
 */
public interface PurchaseOrderManager {

  ResponseEvent<List<PurchaseOrderDTO>> readAll();
  
  ResponseEvent<List<PurchaseOrderDTO>> readByPerson(QueryPKEvent<String> requestEvent);

  ResponseEvent<PurchaseOrderDTO> read(QueryPKEvent<String> requestEvent);

  ResponseEvent<PurchaseOrderDTO> create(CommandEvent<PurchaseOrderDTO> requestEvent);

  ResponseEvent<PurchaseOrderDTO> update(CommandEvent<PurchaseOrderDTO> requestEvent, String id);

  ResponseEvent<String> delete(CommandEvent<String> requestEvent);
}
