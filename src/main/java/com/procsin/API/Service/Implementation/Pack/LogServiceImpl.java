package com.procsin.API.Service.Implementation.Pack;

import com.procsin.API.DAO.Pack.LoginLogDao;
import com.procsin.API.DAO.Pack.OrderLogDao;
import com.procsin.API.DAO.UserDao;
import com.procsin.API.Model.LeadershipResponseEntity;
import com.procsin.API.Model.MyLogsResponseEntity;
import com.procsin.API.Model.StatsResponseModel;
import com.procsin.API.Service.Interface.Pack.LogService;
import com.procsin.DB.Entity.Pack.LoginLog;
import com.procsin.DB.Entity.Pack.OrderLog;
import com.procsin.DB.Entity.UserManagement.User;
import com.procsin.Static.Queries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
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
    @Autowired
    private UserDao userRepository;

    @Autowired
    EntityManager em;

//    @Autowired
//    private MyLogsResponseRepository myLogsResponseRepository;
//    @Autowired
//    private LeadershipResponseRepository leadershipResponseRepository;

    private User getActiveUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername());
    }

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

    @Override
    public List<LeadershipResponseEntity> getLeadership(String type) {
        List<LeadershipResponseEntity> responseModel = new ArrayList<LeadershipResponseEntity>();
        if (type.equals(LeadershipResponseEntity.LeaderShipType.DAILY.toString())) {
            return em.createNativeQuery(Queries.LEADERSHIP_DAILY_QUERY, LeadershipResponseEntity.class).getResultList();
        }
        else if (type.equals(LeadershipResponseEntity.LeaderShipType.MONTHLY.toString())) {
            return em.createNativeQuery(Queries.LEADERSHIP_MONTHLY_QUERY, LeadershipResponseEntity.class).getResultList();
        }
        else if (type.equals(LeadershipResponseEntity.LeaderShipType.ALL.toString())){
            return em.createNativeQuery(Queries.LEADERSHIP_ALL_QUERY, LeadershipResponseEntity.class).getResultList();
        }
        return responseModel;
    }

    @Override
    public StatsResponseModel getMyStats() {
        MyLogsResponseEntity allStats = (MyLogsResponseEntity) em.createNativeQuery(Queries.STATS_MY_ALL_STATS, MyLogsResponseEntity.class).setParameter("userId",getActiveUser().getId()).getSingleResult();
        MyLogsResponseEntity todayStats = (MyLogsResponseEntity) em.createNativeQuery(Queries.STATS_MY_DAILY_STATS, MyLogsResponseEntity.class).setParameter("userId",getActiveUser().getId()).getSingleResult();
        return new StatsResponseModel(todayStats.totalOrderCount,allStats.totalOrderCount,todayStats.totalProductCount,allStats.totalProductCount,todayStats.totalCost,allStats.totalCost);
    }

    @Override
    public List<OrderLog> findMyLogs() {
        List<OrderLog> orderLogs = orderLogRepository.findPreviousOrderLogs(getActiveUser().getId());
        return orderLogs;
    }

}
