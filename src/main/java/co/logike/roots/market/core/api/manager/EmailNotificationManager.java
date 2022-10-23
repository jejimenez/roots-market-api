/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.api.manager;

import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.objects.EmailNotificationDTO;
import co.logike.roots.market.core.api.objects.OrderProductDTO;
import co.logike.roots.market.core.api.objects.OrderProductMailedDTO;
import co.logike.roots.market.core.api.objects.PurchaseOrderDTO;

import java.util.List;

/**
 * Interface manager for {@link DeliveryCycle}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-11-12
 * @since 1.0
 */
public interface EmailNotificationManager {

  ResponseEvent<Boolean> sendNotificationTest();
	  
  ResponseEvent<Boolean> sendNotificationNewPurchase(ResponseEvent<PurchaseOrderDTO> responseEvent, ResponseEvent<List<OrderProductMailedDTO>> responseOrderProducts);
  
}
