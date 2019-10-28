package com.procsin.DB.Entity;

import javax.persistence.*;

@Entity
@Table(name = "Product", schema = "responses", catalog = "PRS_SEVK")
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Product() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
