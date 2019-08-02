package com.procsin.API.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LeadershipResponseEntity {

    public enum LeaderShipType {
        DAILY, MONTHLY, ALL
    }

    @Id
    public String id;

    public String username;

    public int productCount;
    public double cost;
    public int orderCount;

    public LeadershipResponseEntity() {}

    public LeadershipResponseEntity(String id, String username, int productCount, double cost, int orderCount) {
        this.id = id;
        this.username = username;
        this.productCount = productCount;
        this.cost = cost;
        this.orderCount = orderCount;
    }
}