package com.procsin.API.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MyLogsResponseEntity", schema = "responses", catalog = "PRS_SEVK")
public class MyLogsResponseEntity {

    @Id
    public String id;

    public String username;
    public int totalProductCount;
    public double totalCost;
    public int totalOrderCount;

    public MyLogsResponseEntity() {}

    public MyLogsResponseEntity(String id, String username, int totalProductCount, double totalCost, int totalOrderCount) {
        this.id = id;
        this.username = username;
        this.totalProductCount = totalProductCount;
        this.totalCost = totalCost;
        this.totalOrderCount = totalOrderCount;
    }
}
