package com.procsin.API.Controller.Pack;

import com.procsin.API.Model.GenericResponse;
import com.procsin.API.Model.OrderLogSuccessModel;
import com.procsin.API.Model.TSOFT.OrderResponseModel;
import com.procsin.API.Service.Interface.Pack.OrderService;
import com.procsin.DB.Entity.Pack.Orders;
import com.procsin.DB.Entity.Pack.report.OrderReportResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityManager;
import java.util.List;
import static com.procsin.Static.Queries.GET_FAILED_ORDERS;
import static com.procsin.Static.Queries.ORDERS_BY_DATE;

@RequestMapping("/order")
@RestController
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

//    @RequestMapping(value = "/next", method = RequestMethod.POST)
//    public OrderResponseModel getNextOrder(@RequestBody String username) {
//        return orderService.getNextOrder(username);
//    }

//    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
//    public GenericResponse updateOrderStatus(@RequestBody UpdateOrderRequestmodel model) {
//        return orderService.updateOrderStatus(model.username, model.orderCode, model.status);
//    }

//    @RequestMapping(value = "/checkSavedOrder", method = RequestMethod.POST)
//    public GenericResponse fixSavedOrder(@RequestBody UpdateOrderRequestmodel model) {
//        return orderService.fixSavedOrder(model.username, model.orderCode);
//    }

//    @RequestMapping(value = "/finish", method = RequestMethod.POST)
//    public GenericResponse finishOrder(@RequestBody UpdateOrderRequestmodel model) {
//        return orderService.finishOrder(model.username, model.orderCode);
//    }

    @RequestMapping(value = "/prepareOrder", method = RequestMethod.POST)
    OrderLogSuccessModel prepareOrder(@RequestBody OrderResponseModel orderModel) {
        return orderService.prepareOrder(orderModel);
    }

    @RequestMapping(value = "/updateToSupplement", method = RequestMethod.POST)
    GenericResponse updateToSupplement(@RequestBody OrderResponseModel orderModel) {
        return orderService.updateToSupplement(orderModel);
    }

    @RequestMapping(value = "/updateToPackCancel", method = RequestMethod.POST)
    GenericResponse updateToPackCancel(@RequestBody OrderResponseModel orderModel) {
        return orderService.updateToPackCancel(orderModel.OrderCode);
    }

    @RequestMapping(value = "/finishOrder", method = RequestMethod.POST)
    GenericResponse finishOrder(@RequestBody OrderResponseModel orderModel) {
        return orderService.finishOrder(orderModel);
    }

    @RequestMapping(value = "/isPacked", method = RequestMethod.GET)
    GenericResponse isPackedBefore(@RequestParam String orderCode) {
        GenericResponse response = new GenericResponse();
        response.success = orderService.isPackedBefore(orderCode);
        return response;
    }

}
