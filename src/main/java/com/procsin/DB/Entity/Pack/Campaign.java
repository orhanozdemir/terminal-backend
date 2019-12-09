package com.procsin.DB.Entity.Pack;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Campaign", schema = "sevk", catalog = "PRS_SEVK")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String campaignType;

    private String productCode;
    private String productBarcode;
    private String productName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double costThreshold;

    private boolean iosAvailable;
    private boolean androidAvailable;
    private boolean webAvailable;

    private boolean isActive;

    private Date createdAt;

    public Campaign() {}

    public Campaign(String description, double costThreshold, boolean isActive, Date createdAt) {
        this.description = description;
        this.costThreshold = costThreshold;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(String campaignType) {
        this.campaignType = campaignType;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCostThreshold() {
        return costThreshold;
    }

    public void setCostThreshold(double costThreshold) {
        this.costThreshold = costThreshold;
    }

    public boolean isIosAvailable() {
        return iosAvailable;
    }

    public void setIosAvailable(boolean iosAvailable) {
        this.iosAvailable = iosAvailable;
    }

    public boolean isAndroidAvailable() {
        return androidAvailable;
    }

    public void setAndroidAvailable(boolean androidAvailable) {
        this.androidAvailable = androidAvailable;
    }

    public boolean isWebAvailable() {
        return webAvailable;
    }

    public void setWebAvailable(boolean webAvailable) {
        this.webAvailable = webAvailable;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
