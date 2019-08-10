package com.procsin.API.Model.ResponseModel;

public class FailUserStatsModel {

    public String username;
    public int failCount;
    public int orderCount;

    public FailUserStatsModel() {}

    public FailUserStatsModel(Object[] objects) {
        objects[0] = username;
        objects[1] = failCount;
        objects[2] = orderCount;
    }
}
