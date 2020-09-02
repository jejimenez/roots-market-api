/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * OrderTracking entity.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Entity
@Data
@Table(name = "order_tracking", schema = "public")
public class OrderTracking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_")
    private Long id;

    @Basic(optional = false)
    @Column(name = "update_time_")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Column(name = "description_")
    private String description;

    @Column(name = "eth_txn_")
    private String ethTxn;

    @JoinColumn(name = "order_status_", referencedColumnName = "id_")
    @ManyToOne(optional = false)
    private OrderStatus orderStatus;

    @JoinColumn(name = "person_", referencedColumnName = "id_")
    @ManyToOne(optional = false)
    private Person person;

    @JoinColumn(name = "purchase_order_", referencedColumnName = "id_")
    @ManyToOne(optional = false)
    private PurchaseOrder purchaseOrder;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderTracking that = (OrderTracking) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(description, that.description) &&
                Objects.equals(ethTxn, that.ethTxn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, updateTime, description, ethTxn);
    }
}
