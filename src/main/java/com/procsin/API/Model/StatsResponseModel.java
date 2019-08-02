package com.procsin.API.Model;

public class StatsResponseModel {

    public int dailyOrderCount;
    public int totalOrderCount;
    public int dailyProductCount;
    public int totalProductCount;
    public double dailyCost;
    public double totalCost;

    public StatsResponseModel() {}
    public StatsResponseModel(int dailyOrderCount, int totalOrderCount, int dailyProductCount, int totalProductCount, double dailyCost, double totalCost) {
        this.dailyOrderCount = dailyOrderCount;
        this.totalOrderCount = totalOrderCount;
        this.dailyProductCount = dailyProductCount;
        this.totalProductCount = totalProductCount;
        this.dailyCost = dailyCost;
        this.totalCost = totalCost;
    }
}
