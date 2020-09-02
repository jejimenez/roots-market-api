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
 * Groups entity.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Entity
@Data
@Table(name = "user_role", schema = "public")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_")
    private Long id;

    @Basic(optional = false)
    @Column(name = "name_")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    private Collection<PersonRoleOrganization> personRoleOrganizationCollection;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole groups = (UserRole) o;
        return Objects.equals(id, groups.id) &&
                Objects.equals(name, groups.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
