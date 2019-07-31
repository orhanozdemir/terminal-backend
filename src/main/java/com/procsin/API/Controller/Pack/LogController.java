package com.procsin.API.Controller.Pack;

import com.procsin.API.Model.LeadershipResponseEntity;
import com.procsin.API.Model.StatsResponseModel;
import com.procsin.API.Service.Interface.Pack.LogService;
import com.procsin.DB.Entity.Pack.OrderLog;
import com.procsin.DB.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/stats")
@RestController
public class LogController {

    private LogService logService;

//    @Autowired
//    public LogController(LogService logService) {
//        this.logService = logService;
//    }
//
//    @RequestMapping(value = "/myLogs", method = RequestMethod.POST)
//    public List<OrderLog> findMyLogs(@RequestBody User user) {
//        return logService.findMyLogs(user);
//    }
//
//    @RequestMapping(value = "/leadership", method = RequestMethod.GET)
//    public List<LeadershipResponseEntity> getLeadershipData(@RequestParam String type) {
//        return logService.getLeadership(type);
//    }
//
//    @RequestMapping(value = "/myStats", method = RequestMethod.POST)
//    public StatsResponseModel findMyStats(@RequestBody User user) {
//        return logService.getMyStats(user);
//    }
//
//    @RequestMapping(value = "/allStats", method = RequestMethod.GET)
//    public void findAllStats() {
//        logService.getAllStats();
//    }

}
