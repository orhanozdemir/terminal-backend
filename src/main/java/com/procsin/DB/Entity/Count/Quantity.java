package com.procsin.DB.Entity.Count;

import com.procsin.DB.Entity.UserManagement.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Quantity", schema = "sevk", catalog = "PRS_SEVK")
public class Quantity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinTable(name="quantity_product", schema = "sevk", catalog = "PRS_SEVK", joinColumns=@JoinColumn(name="quantity_id"), inverseJoinColumns=@JoinColumn(name="product_code"))
//    private Product product;

    @ManyToOne
    @JoinTable(name="quantity_shelf", schema = "sevk", catalog = "PRS_SEVK", joinColumns=@JoinColumn(name="quantity_id"), inverseJoinColumns=@JoinColumn(name="shelf_id"))
    private Shelf shelf;

    @ManyToOne
    @JoinTable(name="quantity_enumeration", schema = "sevk", catalog = "PRS_SEVK", joinColumns=@JoinColumn(name="quantity_id"), inverseJoinColumns=@JoinColumn(name="enumeration_id"))
    private Enumeration enumeration;

    private String productCode;
    private String productBarcode;
    private String productName;

    private int count;

    @ManyToOne
    @JoinTable(name="quantity_createdBy", schema = "sevk", catalog = "PRS_SEVK", joinColumns=@JoinColumn(name="quantity_id"), inverseJoinColumns=@JoinColumn(name="account_id"))
    private User createdBy;

    private Date createdAt;

    public Quantity() {}

    public Quantity(Shelf shelf, Enumeration enumeration, String productCode, String productBarcode, String productName, int count, User createdBy, Date createdAt) {
        this.shelf = shelf;
        this.enumeration = enumeration;
        this.productCode = productCode;
        this.productBarcode = productBarcode;
        this.productName = productName;
        this.count = count;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    //    public Quantity(Product product, Shelf shelf, Enumeration enumeration, String productBarcode, String productName, int count, User createdBy, Date createdAt) {
//        this.product = product;
//        this.shelf = shelf;
//        this.enumeration = enumeration;
//        this.productBarcode = productBarcode;
//        this.productName = productName;
//        this.count = count;
//        this.createdBy = createdBy;
//        this.createdAt = createdAt;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }

    public Enumeration getEnumeration() {
        return enumeration;
    }

    public void setEnumeration(Enumeration enumeration) {
        this.enumeration = enumeration;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductBarcode() {
        return productBarcode;
    }

    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
