/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.api.manager;

import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.objects.DeliveryCycleDTO;
import co.logike.roots.market.core.app.entity.DeliveryCycle;


import java.util.List;

/**
 * Interface manager for {@link DeliveryCycle}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-11-12
 * @since 1.0
 */
public interface DeliveryCycleManager {

  ResponseEvent<List<DeliveryCycleDTO>> readAll();

  ResponseEvent<DeliveryCycleDTO> read(QueryPKEvent<String> requestEvent);
  
  ResponseEvent<DeliveryCycleDTO> readLast();
  
}
