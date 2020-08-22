/*
 * Logike.co - deRaíz.
 * 2020.
 */
package co.logike.roots.market.core.app.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@Table(catalog = "roots", schema = "public")
public class Product {

    @Id
    @Column(name = "product_id")
    private Integer productId;

    @Basic
    @Column(name = "units_id")
    private Integer unitsId;
    @Basic
    @Column(name = "category_id")
    private Integer categoryId;
    @Basic
    @Column(name = "product_name")
    private String productName;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "product_cost")
    private BigInteger productCost;

    @Basic
    @Column(name = "product_label")
    private String productLabel;
    @Basic
    @Column(name = "user_organization_id")
    private Integer userOrganizationId;

}
