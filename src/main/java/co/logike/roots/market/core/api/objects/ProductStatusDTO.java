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
public class ProductStatusDTO implements Serializable {

	private static final long serialVersionUID = -7514772030089564628L;
	
	private String id;
    private String name;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
