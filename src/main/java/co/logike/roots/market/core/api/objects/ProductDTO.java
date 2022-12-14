/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.api.objects;

import com.google.gson.Gson;
import lombok.Data;

import java.io.Serializable;

/**
 * Data transfer object for the Product entity.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Data
public class ProductDTO implements Serializable {

    private static final long serialVersionUID = -3930835383784966694L;

    private String id;
    private String name;
    private String description;
    private String category;
    private String categoryGroup;
    private String producer;
    private String quantity;
    private String unit;
    private String cost;
    private String labels;
    private String image;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
