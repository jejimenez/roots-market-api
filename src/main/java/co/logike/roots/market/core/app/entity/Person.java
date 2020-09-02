/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

/**
 * Users entity.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Entity
@Data
@Table(name = "person", schema = "public")
public class Person implements Serializable {

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
    @Column(name = "nickname_")
    private String nickname;

    @Basic(optional = false)
    @Column(name = "email_")
    private String email;

    @Basic(optional = false)
    @Column(name = "password_")
    private String password;

    @Column(name = "address_")
    private String address;

    @Column(name = "map_latitude_")
    private BigDecimal mapLatitude;

    @Column(name = "map_longitude_")
    private BigDecimal mapLongitude;

    @Column(name = "eth_key_")
    private String ethKey;

    @Column(name = "eth_address_")
    private String ethAddress;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private Collection<PersonRoleOrganization> personRoleOrganizationCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private Collection<PurchaseOrder> purchaseOrderCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private Collection<Review> reviewCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private Collection<OrderTracking> orderTrackingCollection;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person users = (Person) o;
        return Objects.equals(id, users.id) &&
                Objects.equals(nickname, users.nickname) &&
                Objects.equals(email, users.email) &&
                Objects.equals(name, users.name) &&
                Objects.equals(password, users.password) &&
                Objects.equals(address, users.address) &&
                Objects.equals(mapLatitude, users.mapLatitude) &&
                Objects.equals(mapLongitude, users.mapLongitude) &&
                Objects.equals(ethKey, users.ethKey) &&
                Objects.equals(ethAddress, users.ethAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, email, name, password, address, mapLatitude, mapLongitude, ethKey, ethAddress);
    }
}
