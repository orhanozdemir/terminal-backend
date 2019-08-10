package com.procsin.API.Model.ResponseModel;

import java.util.List;

public class FailStatsResponseModel {

    public int failCount;
    public int orderCount;

    public List<FailUserStatsModel> userStats;

    public FailStatsResponseModel() {}

    public FailStatsResponseModel(Object[] objects) {
        objects[0] = failCount;
        objects[1] = orderCount;
    }
}
