/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.parser;

import co.logike.roots.market.core.api.objects.CategoryDTO;
import co.logike.roots.market.core.app.entity.Category;

/**
 * Product produced parser.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-09-05
 * @since 1.0
 */
public class CategoryParser {

    public static CategoryDTO setCategoryDTO(Category entity) {
    	CategoryDTO domain = new CategoryDTO();
        domain.setName(entity.getName());
        domain.setId(entity.getId().toString());
        domain.setOrderNum(entity.getOrderNum().toString());
        domain.setGroupCategory(entity.getCategoryGroup().getId().toString());
        return domain;
    }

    public static Category setCategory(CategoryDTO domain) {
    	Category entity = new Category();
        return getCategory(entity, domain);
    }

    public static void setCategory(Category categoryToUpdate, CategoryDTO domain) {
        getCategory(categoryToUpdate, domain);
    }

    private static Category getCategory(Category categoryToUpdate, CategoryDTO domain) {
    	categoryToUpdate.setName(domain.getName());
        return categoryToUpdate;
    }
}
