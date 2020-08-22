/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.api.events;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * A event providing information on an query command with filter.
 *
 * @param <E>
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class QueryPKEvent<E extends Serializable> {

  private E request;
  private Map<String, String> filters;

  QueryPKEvent(E request, Map<String, String> filters) {
    this.request = request;
    this.filters = filters;
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }
}
