package com.procsin.DB.Entity.Count;

import com.procsin.DB.Entity.Shop;
import com.procsin.DB.Entity.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Enumeration", schema = "test", catalog = "PRS_SEVK")
public class Enumeration {

    public enum EnumStatus {
        ACTIVE, FINISHED, CANCELLED
    }

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinTable(name="enumeration_shop", schema = "test", catalog = "PRS_SEVK", joinColumns=@JoinColumn(name="enumeration_id"), inverseJoinColumns=@JoinColumn(name="shop_id"))
    private Shop shop;

//    @ManyToMany
//    @JoinTable(name="enumeration_shelf", schema = "sevk", catalog = "PRS_SEVK", joinColumns=@JoinColumn(name="enumeration_id"), inverseJoinColumns=@JoinColumn(name="shelf_id"))
//    private List<Shelf> shelves;

    private String status;
    private String barcode;

    private Date createdAt;
    private Date updatedAt;

    @ManyToOne
    @JoinTable(name="enumeration_createdBy", schema = "test", catalog = "PRS_SEVK", joinColumns=@JoinColumn(name="enumeration_id"), inverseJoinColumns=@JoinColumn(name="account_id"))
    private User createdBy;

    @ManyToOne
    @JoinTable(name="enumeration_updatedBy", schema = "test", catalog = "PRS_SEVK", joinColumns=@JoinColumn(name="enumeration_id"), inverseJoinColumns=@JoinColumn(name="account_id"))
    private User updatedBy;

    public Enumeration() {}

    public Enumeration(Shop shop, String status, String barcode, Date createdAt, Date updatedAt, User createdBy, User updatedBy) {
        this.shop = shop;
        this.status = status;
        this.barcode = barcode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

//    public List<Shelf> getShelves() {
//        return shelves;
//    }
//
//    public void setShelves(List<Shelf> shelves) {
//        this.shelves = shelves;
//    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }
}
