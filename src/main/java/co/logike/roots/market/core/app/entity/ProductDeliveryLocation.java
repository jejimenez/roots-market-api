package co.logike.roots.market.core.app.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
/**
 * ProductDeliveryLocation entity.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jiménez</a>
 * @version 1.0 2021-07-07
 * @since 1.0
 */
@Entity
@Data
@Table(name = "product_delivery_location", schema = "public")
public class ProductDeliveryLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_")
    private Long id;

    @JoinColumn(name = "product_", referencedColumnName = "id_")
    @ManyToOne(optional = false)
    private Product product;

    @JoinColumn(name = "delivery_location_", referencedColumnName = "id_")
    @ManyToOne(optional = false)
    private DeliveryLocation deliveryLocation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDeliveryLocation that = (ProductDeliveryLocation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
