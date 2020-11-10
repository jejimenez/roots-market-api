/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.api.manager;

import co.logike.roots.market.core.api.events.CommandEvent;
import co.logike.roots.market.core.api.events.QueryPKEvent;
import co.logike.roots.market.core.api.events.ResponseEvent;
import co.logike.roots.market.core.api.objects.PersonDTO;
import co.logike.roots.market.core.app.entity.Person;


import java.util.List;

/**
 * Interface manager for {@link Person}.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-06
 * @since 1.0
 */
public interface PersonManager {

  ResponseEvent<List<PersonDTO>> readAll();

  ResponseEvent<PersonDTO> read(QueryPKEvent<String> requestEvent);

  ResponseEvent<PersonDTO> readByEmail(QueryPKEvent<String> requestEvent);

  ResponseEvent<PersonDTO> create(CommandEvent<PersonDTO> requestEvent);

  ResponseEvent<PersonDTO> login(CommandEvent<PersonDTO> requestEvent);

  ResponseEvent<PersonDTO> update(CommandEvent<PersonDTO> requestEvent, String id);

  ResponseEvent<String> delete(CommandEvent<String> requestEvent);
}
