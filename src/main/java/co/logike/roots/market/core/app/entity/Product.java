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
 * Product entity.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Entity
@Data
@Table(name = "product", schema = "public")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @Basic(optional = false)
    @Column(name = "id_")
    private Long id;

    @Basic(optional = false)
    @Column(name = "name_")
    private String name;

    @Column(name = "description_")
    private String description;

    @Basic(optional = false)
    @Column(name = "quantity_")
    private BigDecimal quantity;

    @Basic(optional = false)
    @Column(name = "cost_")
    private BigDecimal cost;

    @Column(name = "labels_")
    private String labels;

    @Column(name = "image_")
    private String image;

    @JoinColumn(name = "category_", referencedColumnName = "id_")
    @ManyToOne(optional = false)
    private Category category;

    @JoinColumn(name = "producer_", referencedColumnName = "id_")
    @ManyToOne(optional = false)
    private PersonRoleOrganization producer;

    @JoinColumn(name = "unit_", referencedColumnName = "id_")
    @ManyToOne(optional = false)
    private Unit unit;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Collection<OrderProduct> orderProductCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Collection<Review> reviewCollection;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(quantity, product.quantity) &&
                Objects.equals(cost, product.cost) &&
                Objects.equals(labels, product.labels) &&
                Objects.equals(image, product.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, quantity, cost, labels);
    }

}
