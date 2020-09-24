/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.api.objects;

import com.google.gson.Gson;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Data transfer object for the Purchase_Order entity.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jiménez</a>
 * @version 1.0 2020-09-18
 * @since 1.0
 */
@Data
public class PurchaseOrderDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
    private String person;
    private String orderStatus;
    private String request;
    
    private List<OrderProductDTO> orderProducts;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
