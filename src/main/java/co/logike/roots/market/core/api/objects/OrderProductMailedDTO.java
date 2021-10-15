/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.api.objects;

import com.google.gson.Gson;
import lombok.Data;

import java.io.Serializable;

/**
 * Data transfer object for the ProductStatus entity.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-05
 * @since 1.0
 */
@Data
public class OrderProductMailedDTO implements Serializable {

	private static final long serialVersionUID = -1914772171089509055L;
	
	private String id;
    private String purchaseOrder;
    private String product;
    private String productStatus;
    private String units;
    private String description;

    private String quantity;
    private String unit;
    

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
