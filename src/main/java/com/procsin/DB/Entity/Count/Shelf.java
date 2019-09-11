package com.procsin.DB.Entity.Count;

import com.procsin.DB.Entity.Shop;
import com.procsin.DB.Entity.UserManagement.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Shelf", schema = "sevk", catalog = "PRS_SEVK")
public class Shelf {

    public enum ShelfType {
        RAF, STAND, DEPO
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinTable(name="shelf_shop", schema = "sevk", catalog = "PRS_SEVK", joinColumns=@JoinColumn(name="shelf_id"), inverseJoinColumns=@JoinColumn(name="shop_id"))
    private Shop shop;

    private ShelfType type;
    private int section;
    private int row;

    private String name;

    @Column(nullable = false, unique = true)
    private String barcode;

    @ManyToOne
    @JoinTable(name="shelf_createdBy", schema = "sevk", catalog = "PRS_SEVK", joinColumns=@JoinColumn(name="shelf_id"), inverseJoinColumns=@JoinColumn(name="account_id"))
    private User createdBy;

    @ManyToOne
    @JoinTable(name="shelf_updatedBy", schema = "sevk", catalog = "PRS_SEVK", joinColumns=@JoinColumn(name="shelf_id"), inverseJoinColumns=@JoinColumn(name="account_id"))
    private User updatedBy;

    private Date createdAt;
    private Date updatedAt;

    private Boolean isActive;

    public Shelf() {}

    public Shelf(Shop shop, ShelfType type, int section, int row, String name, String barcode, User createdBy, User updatedBy, Date createdAt, Date updatedAt, Boolean isActive) {
        this.shop = shop;
        this.type = type;
        this.section = section;
        this.row = row;
        this.name = name;
        this.barcode = barcode;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
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

    public ShelfType getType() {
        return type;
    }

    public void setType(ShelfType type) {
        this.type = type;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
