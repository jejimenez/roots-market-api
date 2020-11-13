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
 * DeliveryCycle entity.
 *
 * @author <a href="mailto:jimenez.ing.sis@gmail.com">Jaime Jimenez</a>
 * @version 1.0 2020-11-12
 * @since 1.0
 */
@Entity
@Data
@Table(name = "delivery_cycle", schema = "public")
public class DeliveryCycle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "delivery_cycle_id_seq", sequenceName = "delivery_cycle_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_cycle_id_seq")
    @Basic(optional = false)
    @Column(name = "id_")
    private Long id;
    
    @Column(name = "delivery_date_")
    @Temporal(TemporalType.DATE)
    private Date deliveryDate;
    
    @Column(name = "close_date_")
    @Temporal(TemporalType.DATE)
    private Date closeDate;

    @JoinColumn(name = "organization_", referencedColumnName = "id_")
    @ManyToOne(optional = false)
    private Organization organization;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryCycle that = (DeliveryCycle) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(deliveryDate, that.deliveryDate) &&
                Objects.equals(closeDate, that.closeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deliveryDate, closeDate);
    }

}
