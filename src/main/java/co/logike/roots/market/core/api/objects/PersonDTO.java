/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.api.objects;

import com.google.gson.Gson;
import lombok.Data;

import java.io.Serializable;

/**
 * Data transfer object for the Person entity.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-06
 * @since 1.0
 */
@Data
public class PersonDTO implements Serializable {

	private static final long serialVersionUID = -8440939470619402749L;
	
	private String id;
    private String nickname;
    private String email;
    private String name;
    private String password;
    private String address;
    private String city;
    private String state;
    private String telephone;
    private String mapLatitude;
    private String mapLongitude;
    private String ethKey;
    private String ethAddress;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
