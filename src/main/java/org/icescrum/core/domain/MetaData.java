package org.icescrum.core.domain;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "is_metadata")
public class MetaData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "meta_key")
    private String key;
    private String value;
    private int orderNumber = 0;

    public MetaData() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public int getOrderNumber() { return orderNumber; }
    public void setOrderNumber(int orderNumber) { this.orderNumber = orderNumber; }
}
