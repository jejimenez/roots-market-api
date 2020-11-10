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
 * Person entity.
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
    @SequenceGenerator(name = "person_id_seq", sequenceName = "person_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_id_seq")
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

    @Column(name = "city_")
    private String city;

    @Column(name = "state_")
    private String state;

    @Column(name = "telephone_")
    private String telephone;

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
        Person persons = (Person) o;
        return Objects.equals(id, persons.id) &&
                Objects.equals(nickname, persons.nickname) &&
                Objects.equals(email, persons.email) &&
                Objects.equals(name, persons.name) &&
                Objects.equals(password, persons.password) &&
                Objects.equals(address, persons.address) &&
                Objects.equals(address, persons.city) &&
                Objects.equals(address, persons.state) &&
                Objects.equals(address, persons.telephone) &&
                Objects.equals(mapLatitude, persons.mapLatitude) &&
                Objects.equals(mapLongitude, persons.mapLongitude) &&
                Objects.equals(ethKey, persons.ethKey) &&
                Objects.equals(ethAddress, persons.ethAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, email, name, password, address, city, state, telephone, mapLatitude, mapLongitude, ethKey, ethAddress);
    }
}
