package com.procsin.DB.Entity;

import javax.persistence.*;

@Entity
@Table(name = "Shop", schema = "sevk", catalog = "PRS_SEVK")
public class Shop {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String shopCode;

    @Column(nullable = false)
    private String shopName;

    private Boolean isActive;

    public Shop() {}

    public Shop(String shopCode, String shopName, Boolean isActive) {
        this.shopCode = shopCode;
        this.shopName = shopName;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
