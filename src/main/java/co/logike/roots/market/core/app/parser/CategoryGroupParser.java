/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.parser;

import co.logike.roots.market.core.api.objects.CategoryDTO;
import co.logike.roots.market.core.api.objects.CategoryGroupDTO;
import co.logike.roots.market.core.app.entity.Category;
import co.logike.roots.market.core.app.entity.CategoryGroup;

/**
 * CategoryGroupParser parser.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-05
 * @since 1.0
 */
public class CategoryGroupParser{

    public static CategoryGroupDTO setCategoryGroupDTO(CategoryGroup entity) {
    	CategoryGroupDTO domain = new CategoryGroupDTO();
        domain.setName(entity.getName());
        domain.setId(entity.getId().toString());
        domain.setOrderNum(entity.getOrderNum().toString());
        domain.setOrganization(entity.getOrganization().getId().toString());
        return domain;
    }

    public static CategoryGroup setCategoryGroup(CategoryGroupDTO domain) {
    	CategoryGroup entity = new CategoryGroup();
        return getCategoryGroup(entity, domain);
    }

    public static void setCategoryGroup(CategoryGroup categoryToUpdate, CategoryGroupDTO domain) {
    	getCategoryGroup(categoryToUpdate, domain);
    }

    private static CategoryGroup getCategoryGroup(CategoryGroup categoryToUpdate, CategoryGroupDTO domain) {
    	categoryToUpdate.setName(domain.getName());
        return categoryToUpdate;
    }
}