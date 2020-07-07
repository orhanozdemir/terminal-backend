package com.procsin.API.Controller.Pack;

import com.procsin.API.Model.GenericResponse;
import com.procsin.API.Model.ReturnOrderRequestModel;
import com.procsin.API.Service.Interface.Pack.ReturnedOrderService;
import com.procsin.DB.Entity.Pack.Return.ReturnedOrder;
import com.procsin.Retrofit.Models.TSoft.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/order")
@RestController
public class OrderController {

    @Autowired
    ReturnedOrderService returnedOrderService;

    @RequestMapping(value = "/returnOrder", method = RequestMethod.POST)
    GenericResponse returnOrders(@RequestBody ReturnOrderRequestModel model) {
        returnedOrderService.createReturnedOrder(model);
        return new GenericResponse(true, "Başarılı");
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

    @CrossOrigin
    @RequestMapping(value = "/updateReturnedOrder", method = RequestMethod.POST)
    ReturnedOrder updateReturnedOrder(@RequestBody ReturnedOrder param) {
        return returnedOrderService.updateReturnedOrder(param.id, param.status, param.description, param.trackingCode);
    }

    @RequestMapping(value = "/try", method = RequestMethod.GET)
    void sth(@RequestParam String token) {
        returnedOrderService.updateRequiredFields(token);
    }

}
