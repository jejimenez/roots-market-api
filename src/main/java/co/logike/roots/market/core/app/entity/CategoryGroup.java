package co.logike.roots.market.core.app.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

/**
 * CategoryGroup entity.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jiménez</a>
 * @version 1.0 2022-10-07
 * @since 1.0
 */
@Entity
@Data
@Table(name = "category_group", schema = "public")
public class CategoryGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_")
    private Long id;

    @Basic(optional = false)
    @Column(name = "order_num_")
    private String orderNum;

    @Basic(optional = false)
    @Column(name = "name_")
    private String name;
    
    @JoinColumn(name = "organization_", referencedColumnName = "id_")
    @ManyToOne(optional = false)
    private Organization organization;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoryGroup")
    private Collection<Category> categoryCollection;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryGroup categoryGroup = (CategoryGroup) o;
        return Objects.equals(id, categoryGroup.id) &&
                Objects.equals(name, categoryGroup.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
