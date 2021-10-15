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
public class EmailNotificationDTO implements Serializable {

	private static final long serialVersionUID = -84409394706199849L;
	
	private String[] to;
	private String content;
	private String subject;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
