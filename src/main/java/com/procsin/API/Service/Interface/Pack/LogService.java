package com.procsin.API.Service.Interface.Pack;

import com.procsin.API.Model.StatsResponseModel;
import com.procsin.DB.Entity.Pack.FixLog;
import com.procsin.DB.Entity.Pack.LoginLog;
import com.procsin.DB.Entity.Pack.OrderLog;
import java.util.List;

public interface LogService {

    List<OrderLog> findMyLogs();
    StatsResponseModel getMyStats();
    List getLeadership(String type);
    LoginLog createLoginLog(LoginLog loginLog);
    FixLog createFixLog(String orderCode, OrderLog.OrderStatus toStatus);

}
