package com.procsin.API.Controller.Pack;

import com.procsin.API.Model.GenericResponse;
import com.procsin.API.Service.Interface.Pack.ReturnedOrderService;
import com.procsin.DB.Entity.Pack.Return.ReturnedOrder;
import com.procsin.Retrofit.Models.TSoft.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/web")
@RestController
public class OrderController {

    @Autowired
    ReturnedOrderService returnedOrderService;

    @RequestMapping(value = "/returnOrders", method = RequestMethod.POST)
    GenericResponse returnOrders(@RequestBody List<OrderModel> orders) {
        return null;
    }

    @CrossOrigin
    @RequestMapping(value = "/returnedList", method = RequestMethod.GET)
    List<ReturnedOrder> returnedOrderList(@RequestParam ReturnedOrder.ReturnedOrderStatus status) {
        return returnedOrderService.returnedOrdersByStatus(status);
    }

    @CrossOrigin
    @RequestMapping(value = "/allReturnedList", method = RequestMethod.GET)
    List<ReturnedOrder> returnedOrderList() {
        return returnedOrderService.allReturnedOrders();
    }

}
