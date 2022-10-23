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
public class OrderGroupingProducerReport implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "grouping_producer")
    private char groupingProducer;

    @Column(name = "grouping_client")
    private char groupingClient;

    @Column(name = "producer")
    private String producer;

    @Column(name = "product")
    private String product;

    @Column(name = "packaging")
    private String packaging;

    @Column(name = "client")
    private String client;

    @Column(name = "creation_time")
    private String creationTime;

    @Column(name = "units")
    private BigDecimal units;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "total")
    private BigDecimal total;
    

}
