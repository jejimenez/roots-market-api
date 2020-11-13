/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.api.objects;

import com.google.gson.Gson;
import lombok.Data;

import java.io.Serializable;

/**
 * Data transfer object for the DeliveryCycle entity.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-06
 * @since 1.0
 */
@Data
public class DeliveryCycleDTO implements Serializable {

	private static final long serialVersionUID = -8440939470619982349L;
	
	private String id;
    private String deliveryDate;
    private String closeDate;
    private String organization;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
