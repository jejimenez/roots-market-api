package co.logike.roots.market.core.app.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Data
public class OrderReport  implements Serializable {

    @Id
    @Column(name = "orden_de_compra")
    private String id;

    @Column(name = "producto")
    private String product;

    @Column(name = "cantidad")
    private BigDecimal units;

    @Column(name = "costo")
    private BigDecimal cost;

    @Column(name = "productor")
    private String organization;

}
