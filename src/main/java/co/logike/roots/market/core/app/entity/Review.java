/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Review entity.
 *
 * @author <a href="mailto:javier.latorre@logike.co">Javier Latorre</a>
 * @version 1.0 2020-09-01
 * @since 1.0
 */
@Entity
@Data
@Table(name = "review", schema = "public")
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_")
    private Long id;

    @Basic(optional = false)
    @Column(name = "score_")
    private int score;

    @Column(name = "description_")
    private String description;

    @JoinColumn(name = "user_", referencedColumnName = "id_")
    @ManyToOne(optional = false)
    private Person person;

    @JoinColumn(name = "product_", referencedColumnName = "id_")
    @ManyToOne(optional = false)
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id) &&
                Objects.equals(description, review.description) &&
                Objects.equals(score, review.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, score);
    }
}
