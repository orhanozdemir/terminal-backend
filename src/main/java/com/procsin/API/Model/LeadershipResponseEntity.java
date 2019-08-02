package com.procsin.API.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class LeadershipResponseEntity {

    public enum LeaderShipType {
        DAILY, MONTHLY, ALL
    }

    @Id
    @GeneratedValue
    public Long id;

    public String username;

    public double cost;
    public int productCount;
    public int orderCount;

    public LeadershipResponseEntity() {}

    public LeadershipResponseEntity(String username, double cost, int productCount, int orderCount) {
        this.username = username;
        this.cost = cost;
        this.productCount = productCount;
        this.orderCount = orderCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }
}