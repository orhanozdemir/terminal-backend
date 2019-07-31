package com.procsin.API.Service.Implementation.Pack;

import com.procsin.API.DAO.Pack.LoginLogDao;
import com.procsin.API.DAO.Pack.OrderLogDao;
import com.procsin.API.Model.LeadershipResponseEntity;
import com.procsin.API.Model.StatsResponseModel;
import com.procsin.API.Service.Interface.Pack.LogService;
import com.procsin.DB.Entity.Pack.LoginLog;
import com.procsin.DB.Entity.Pack.OrderLog;
import com.procsin.DB.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LoginLogDao loginLogRepository;
    @Autowired
    private OrderLogDao orderLogRepository;
//    @Autowired
//    private MyLogsResponseRepository myLogsResponseRepository;
//    @Autowired
//    private LeadershipResponseRepository leadershipResponseRepository;

    private Boolean isToday(Date date) {
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String todayString = format.format(new Date());
        String dateString = format.format(date);
        return todayString.equals(dateString);
    }

    private Boolean isThisMonth(Date date) {
        DateFormat format = new SimpleDateFormat("yyyyMM");
        String thisMonthString = format.format(new Date());
        String dateString = format.format(date);
        return thisMonthString.equals(dateString);
    }

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    private String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(yesterday());
    }

    @Override
    public LoginLog createLoginLog(LoginLog loginLog) {
        return loginLogRepository.save(loginLog);
    }

//    @Override
//    public List<LeadershipResponseEntity> getLeadership(String type) {
//        List<LeadershipResponseEntity> responseModel = new ArrayList<LeadershipResponseEntity>();
//
//        if (type.equals(LeadershipResponseEntity.LeaderShipType.DAILY.toString())) {
//            responseModel = leadershipResponseRepository.getLeadershipDataForToday();
//        }
//        else if (type.equals(LeadershipResponseEntity.LeaderShipType.MONTHLY.toString())) {
//            responseModel = leadershipResponseRepository.getLeadershipDataForMonth();
//        }
//        else if (type.equals(LeadershipResponseEntity.LeaderShipType.ALL.toString())){
//            responseModel = leadershipResponseRepository.getAllLeadershipData();
//        }
//
////        List<OrderLog> logs = orderLogRepository.findAllByStatus(OrderLog.OrderStatus.KARGO_HAZIR);
////        Map<User, LeadershipResponseEntity> map = new HashMap<User, LeadershipResponseEntity>();
////        for (OrderLog log : logs) {
////            if (map.get(log.getUser()) == null) {
////                LeadershipResponseEntity model = new LeadershipResponseEntity();
////                model.user = log.getUser();
////                map.put(log.getUser(), model);
////            }
////            LeadershipResponseEntity temp = map.get(log.getUser());
////            if (isToday(log.getDate())) {
////                temp.dailyCost += log.getOrder().getTotalCost();
////                temp.dailyProductCount += log.getOrder().getTotalProductCount();
////                temp.dailyOrderCount++;
////            }
////            if (isThisMonth(log.getDate())) {
////                temp.monthlyCost += log.getOrder().getTotalCost();
////                temp.monthlyProductCount += log.getOrder().getTotalProductCount();
////                temp.monthlyOrderCount++;
////            }
////            temp.allCost += log.getOrder().getTotalCost();
////            temp.allProductCount += log.getOrder().getTotalProductCount();
////            temp.allOrderCount++;
////            map.put(log.getUser(), temp);
////        }
////        for (User user : map.keySet()) {
////            responseModel.add(map.get(user));
////        }
//        return responseModel;
//    }
//
//    @Override
//    public StatsResponseModel getMyStats(User user) {
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String today = format.format(new Date());
//        String yesterday = getYesterdayDateString();
//        MyLogsResponseEntity allStats = myLogsResponseRepository.findMyStats(user.getId());
//        MyLogsResponseEntity todayStats = myLogsResponseRepository.findMyDailyStats(user.getId());
//        return new StatsResponseModel(todayStats.totalOrderCount,allStats.totalOrderCount,todayStats.totalProductCount,allStats.totalProductCount,todayStats.totalCost,allStats.totalCost);
//    }
//
//    @Override
//    public List<OrderLog> findMyLogs(User user) {
//        List<OrderLog> orderLogs = orderLogRepository.findPreviousOrderLogs(user.getId());
//        return orderLogs;
//    }
//
//    @Override
//    public void getAllStats() {
//        int count = 0;
//        long total = 0;
//        List<OrderLog> allFinishedLogs = orderLogRepository.findAllByStatus(OrderLog.OrderStatus.KARGO_HAZIR);
//        for (OrderLog finishLog : allFinishedLogs) {
//            OrderLog startLog = orderLogRepository.findStartLog(finishLog.getOrder().getId(), finishLog.getUser().getId());
//            long seconds = (finishLog.getDate().getTime()-startLog.getDate().getTime())/1000;
//            if (seconds > 0) {
//                count++;
//                total += seconds;
//            }
//        }
//        System.out.print("123");
//    }

}
