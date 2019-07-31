package com.procsin.API.Service.Interface.Pack;

import com.procsin.API.Model.LeadershipResponseEntity;
import com.procsin.API.Model.StatsResponseModel;
import com.procsin.DB.Entity.Pack.LoginLog;
import com.procsin.DB.Entity.Pack.OrderLog;
import com.procsin.DB.Entity.User;
import java.util.List;

public interface LogService {

//    void getAllStats();
//    List<OrderLog> findMyLogs(User user);
//    StatsResponseModel getMyStats(User user);
//    List<LeadershipResponseEntity> getLeadership(String type);
    LoginLog createLoginLog(LoginLog loginLog);

}
