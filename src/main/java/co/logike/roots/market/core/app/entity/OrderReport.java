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
public class OrderReport implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "purchase_order")
    private String purchaseOrder;

    @Column(name = "client")
    private String client;

    @Column(name = "product")
    private String product;

    @Column(name = "units")
    private BigDecimal units;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "producer")
    private String producer;

    @Column(name = "creation_time")
    private String creationTime;
    

}
