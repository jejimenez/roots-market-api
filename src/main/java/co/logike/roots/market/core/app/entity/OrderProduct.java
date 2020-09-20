/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * OrderProduct entity.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Entity
@Data
@Table(name = "order_product", schema = "public")
public class OrderProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "order_product_id_seq", sequenceName = "order_product_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_product_id_seq")
    @Basic(optional = false)
    @Column(name = "id_")
    private Long id;

    @Basic(optional = false)
    @Column(name = "units_")
    private BigDecimal units;

    @Column(name = "description_")
    private String description;

    @JoinColumn(name = "product_", referencedColumnName = "id_")
    @ManyToOne(optional = false)
    private Product product;

    @JoinColumn(name = "product_status_", referencedColumnName = "id_")
    @ManyToOne(optional = false)
    private ProductStatus productStatus;

    @JoinColumn(name = "purchase_order_", referencedColumnName = "id_")
    @ManyToOne(optional = false)
    private PurchaseOrder purchaseOrder;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProduct that = (OrderProduct) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(units, that.units) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, units, description);
    }

}
