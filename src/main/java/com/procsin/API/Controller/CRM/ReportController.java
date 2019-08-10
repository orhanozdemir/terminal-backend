package com.procsin.API.Controller.CRM;

import com.procsin.API.Service.Interface.CRM.ReportService;
import com.procsin.DB.Entity.Pack.OrderLog;
import com.procsin.DB.Entity.Pack.Orders;
import com.procsin.DB.Entity.Pack.report.OrderReportResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.List;

import static com.procsin.Static.Queries.GET_FAILED_ORDERS;
import static com.procsin.Static.Queries.ORDERS_BY_DATE;

@Secured({"ROLE_SUPER_ADMIN", "ROLE_CRM", "ROLE_CRM_ADMIN", "ROLE_CRM_MANAGER"})
@RequestMapping("/report")
@RestController
public class ReportController {

    @Autowired
    ReportService reportService;

    @Autowired
    EntityManager em;

//    @RequestMapping(value = "/orders", method = RequestMethod.GET)
//    List<Orders> getOrders(@RequestParam String fromDate, @RequestParam String toDate) {
//        return reportService.getOrders(fromDate,toDate);
//    }
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    List<Orders> getOrders() {
        return reportService.getOrders(null,null);
    }

    @RequestMapping(value = "/orderLogs", method = RequestMethod.GET)
    List<OrderLog> getOrderLogs(@RequestParam Long orderId) {
        return reportService.getOrderLogs(orderId);
    }

    @RequestMapping(value = "/failedList", method = RequestMethod.GET)
    List<OrderReportResponseModel> getFailedOrders() {
        return em.createNativeQuery(GET_FAILED_ORDERS, OrderReportResponseModel.class).getResultList();
    }
    @RequestMapping(value = "/updateFailedStatus", method = RequestMethod.POST)
    Orders updateFailedStatus(@RequestParam Boolean didFail, @RequestParam String orderCode) {
        return reportService.changeFailedStatus(didFail, orderCode);
    }
    @RequestMapping(value = "/findOrder", method = RequestMethod.GET)
    List<Orders> findOrderWithOrderCode(@RequestParam String orderCode) {
        return reportService.findLikeOrderCode(orderCode);
    }

    @RequestMapping(value = "/setOrderDates", method = RequestMethod.GET)
    void setOrderDates() {
        reportService.setOrderDates();
    }

}
