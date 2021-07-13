/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.api.manager;

import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.objects.DeliveryLocationDTO;


import java.util.List;

/**
 * Interface manager for {@link DeliveryLocationDTO}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2021-07-08
 * @since 1.0
 */
public interface DeliveryLocationManager {

  ResponseEvent<List<DeliveryLocationDTO>> readAll();

  ResponseEvent<DeliveryLocationDTO> read(QueryPKEvent<String> requestEvent);
  
  ResponseEvent<List<DeliveryLocationDTO>> findByOrganization(QueryPKEvent<String> requestEvent);
  
}
