/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.parser;

import co.logike.roots.market.core.api.objects.DeliveryCycleDTO;
import co.logike.roots.market.core.app.entity.DeliveryCycle;
import co.logike.roots.market.core.app.entity.Organization;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * DeliveryCycle  parser.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-11-12
 * @since 1.0
 */
public class DeliveryCycleParser {

    public static DeliveryCycleDTO setDeliveryCycleDTO(DeliveryCycle entity) {
        DeliveryCycleDTO domain = new DeliveryCycleDTO();
        domain.setId(entity.getId().toString());
        domain.setDeliveryDate(entity.getDeliveryDate() == null ? "" : entity.getDeliveryDate().toString());
        domain.setCloseDate(entity.getCloseDate() == null ? "" : entity.getCloseDate().toString());
        domain.setOrganization(entity.getOrganization() == null ? "" : entity.getOrganization().getName());
        
        return domain;
    }

    public static DeliveryCycle setDeliveryCycle(DeliveryCycleDTO domain, Organization organization) {
        DeliveryCycle entity = new DeliveryCycle();
        try {
			return getDeliveryCycle(entity, domain, organization);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
    }

    public static void setDeliveryCycle(DeliveryCycle personToUpdate, DeliveryCycleDTO domain, Organization organization) {
        try {
			getDeliveryCycle(personToUpdate, domain, organization);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private static DeliveryCycle getDeliveryCycle(DeliveryCycle personToUpdate, DeliveryCycleDTO domain, Organization organization) throws ParseException {
    	personToUpdate.setDeliveryDate(domain.getDeliveryDate() == null ? null : new SimpleDateFormat("dd/MM/yyyy").parse(domain.getDeliveryDate()));
    	personToUpdate.setCloseDate(domain.getCloseDate() == null ? null : new SimpleDateFormat("dd/MM/yyyy").parse(domain.getCloseDate()));
        personToUpdate.setOrganization(organization);
        return personToUpdate;
    }
}
