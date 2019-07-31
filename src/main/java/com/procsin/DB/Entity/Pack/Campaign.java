package com.procsin.DB.Entity.Pack;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Campaign", schema = "test", catalog = "PRS_SEVK")
public class Campaign {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double costThreshold;

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
