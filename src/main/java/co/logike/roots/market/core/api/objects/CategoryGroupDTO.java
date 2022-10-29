/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.api.objects;

import com.google.gson.Gson;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Data transfer object for the CategoryGroup entity.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-05
 * @since 1.0
 */
@Data
public class CategoryGroupDTO implements Serializable {

	private static final long serialVersionUID = -7514772037712312628L;
	
	private String id;
    private String name;
    private String orderNum;
    private String organization;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}