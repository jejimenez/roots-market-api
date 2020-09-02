/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * Unit entity.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Entity
@Data
@Table(name = "unit", schema = "public")
public class Unit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_")
    private Long id;

    @Basic(optional = false)
    @Column(name = "name_")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unit")
    private Collection<Product> productCollection;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return Objects.equals(id, unit.id) &&
                Objects.equals(name, unit.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
