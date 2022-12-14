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
 * PurchaseOrder entity.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Entity
@Data
@Table(name = "purchase_order", schema = "public")
public class PurchaseOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "purchase_order_id_seq", sequenceName = "purchase_order_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_order_id_seq")
    @Basic(optional = false)
    @Column(name = "id_")
    private Long id;

    @Column(name = "request_")
    private String request;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchaseOrder", fetch = FetchType.EAGER)
    private Collection<OrderProduct> orderProductCollection;

    @JoinColumn(name = "order_status_", referencedColumnName = "id_")
    @ManyToOne(optional = false)
    private OrderStatus orderStatus;

    @JoinColumn(name = "person_", referencedColumnName = "id_")
    @ManyToOne(optional = false)
    private Person person;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchaseOrder")
    private Collection<OrderTracking> orderTrackingCollection;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseOrder that = (PurchaseOrder) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(request, that.request);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, request);
    }
}
