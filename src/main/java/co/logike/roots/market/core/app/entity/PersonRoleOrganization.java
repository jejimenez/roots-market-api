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
 * PersonRoleOrganization entity.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Entity
@Data
@Table(name = "person_role_organization", schema = "public")
public class PersonRoleOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producer")
    private Collection<Product> productCollection;

    @JoinColumn(name = "organization_", referencedColumnName = "id_")
    @ManyToOne(optional = false)
    private Organization organization;

    @JoinColumn(name = "person_", referencedColumnName = "id_")
    @ManyToOne(optional = false)
    private Person person;

    @JoinColumn(name = "role_", referencedColumnName = "id_")
    @ManyToOne(optional = false)
    private UserRole role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonRoleOrganization that = (PersonRoleOrganization) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
