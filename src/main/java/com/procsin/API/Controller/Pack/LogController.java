package com.procsin.API.Controller.Pack;

import com.procsin.API.Model.LeadershipResponseEntity;
import com.procsin.API.Model.StatsResponseModel;
import com.procsin.API.Service.Interface.Pack.LogService;
import com.procsin.DB.Entity.Pack.FixLog;
import com.procsin.DB.Entity.Pack.OrderLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LogController {

    private LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @RequestMapping(value = "/stats/myLogs", method = RequestMethod.POST)
    public List<OrderLog> findMyLogs() {
        return logService.findMyLogs();
    }

    @RequestMapping(value = "/stats/leadership", method = RequestMethod.GET)
    public List<LeadershipResponseEntity> getLeadershipData(@RequestParam String type) {
        return logService.getLeadership(type);
    }

    @RequestMapping(value = "/stats/myStats", method = RequestMethod.POST)
    public StatsResponseModel findMyStats() {
        return logService.getMyStats();
    }

    @RequestMapping(value = "/logs/createFixLog", method = RequestMethod.POST)
    public FixLog createFixLog(@RequestParam String orderCode, @RequestParam OrderLog.OrderStatus toStatus) {
        return logService.createFixLog(orderCode,toStatus);
    }

//    @RequestMapping(value = "/allStats", method = RequestMethod.GET)
//    public void findAllStats() {
//        logService.getAllStats();
//    }

}
