/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.api.events;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A event providing information on an command.
 *
 * @param <E>
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class CommandEvent<E> {

    private E request;

    CommandEvent(E request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
