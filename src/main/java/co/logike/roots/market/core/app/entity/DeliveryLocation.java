package co.logike.roots.market.core.app.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

/**
 * DeliveryLocation entity.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jiménez</a>
 * @version 1.0 2021-07-07
 * @since 1.0
 */
@Entity
@Data
@Table(name = "delivery_location", schema = "public")
public class DeliveryLocation implements Serializable {

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
    @Column(name = "cost_")
    private BigDecimal cost;
    
    @JoinColumn(name = "organization_", referencedColumnName = "id_")
    @ManyToOne(optional = false)
    private Organization organization;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deliveryLocation")
    private Collection<ProductDeliveryLocation> productDeliveryLocationCollection;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryLocation deliveryLocation = (DeliveryLocation) o;
        return Objects.equals(id, deliveryLocation.id) &&
                Objects.equals(name, deliveryLocation.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}