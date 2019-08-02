package com.procsin.API.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class MyLogsResponseEntity {

    @Id
    @GeneratedValue
    public Long id;

    public String username;
    public int totalProductCount;
    public double totalCost;
    public int totalOrderCount;

    public MyLogsResponseEntity() {}
}
