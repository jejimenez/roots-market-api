/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.parser;

import co.logike.roots.market.core.api.objects.DeliveryLocationDTO;
import co.logike.roots.market.core.app.entity.DeliveryLocation;
import co.logike.roots.market.core.app.entity.Organization;

import java.math.BigDecimal;
import java.text.ParseException;

/**
 * DeliveryLocation  parser.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-11-12
 * @since 1.0
 */
public class DeliveryLocationParser {

    public static DeliveryLocationDTO setDeliveryLocationDTO(DeliveryLocation entity) {
        DeliveryLocationDTO domain = new DeliveryLocationDTO();
        domain.setId(entity.getId().toString());
        domain.setName(entity.getName() == null ? "" : entity.getName().toString());
        domain.setCost(entity.getCost() == null ? "" : entity.getCost().toString());
        domain.setOrganization(entity.getOrganization() == null ? "" : entity.getOrganization().getName());
        
        return domain;
    }

    public static DeliveryLocation setDeliveryLocation(DeliveryLocationDTO domain, Organization organization) {
    	DeliveryLocation entity = new DeliveryLocation();
        try {
			return getDeliveryLocation(entity, domain, organization);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
    }

    public static void setDeliveryLocation(DeliveryLocation deliveryLocationToUpdate, DeliveryLocationDTO domain, Organization organization) {
        try {
			getDeliveryLocation(deliveryLocationToUpdate, domain, organization);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private static DeliveryLocation getDeliveryLocation(DeliveryLocation deliveryLocationToUpdate, DeliveryLocationDTO domain, Organization organization) throws ParseException {
    	deliveryLocationToUpdate.setName(domain.getName() == null ? null : domain.getName());
    	deliveryLocationToUpdate.setCost(domain.getCost() == null ? null : new BigDecimal(domain.getCost()));
    	deliveryLocationToUpdate.setOrganization(organization);
        return deliveryLocationToUpdate;
    }
}
