/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

/**
 * Organization entity.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Entity
@Data
@Table(name = "organization", schema = "public")
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_")
    private Long id;

    @Basic(optional = false)
    @Column(name = "name_")
    private String name;

    @Basic(optional = false)
    @Column(name = "creation_date_")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organization")
    private Collection<PersonRoleOrganization> personRoleOrganizationCollection;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creationDate);
    }

}
