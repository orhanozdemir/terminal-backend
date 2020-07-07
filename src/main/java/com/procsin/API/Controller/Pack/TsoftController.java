package com.procsin.API.Controller.Pack;

import com.procsin.API.Model.GenericResponse;
import com.procsin.API.Model.OrderLogSuccessModel;
import com.procsin.API.Service.Interface.Pack.OrderService;
import com.procsin.API.Service.Interface.Pack.TsoftService;
import com.procsin.Retrofit.Models.TSoft.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tsoft")
public class TsoftController {

    @Autowired
    TsoftService tsoftService;

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    OrderLogSuccessModel getOrder(@RequestParam String token, @RequestParam boolean isTrendyol) {
        return orderService.getSingleOrder(token, isTrendyol);
    }

    @RequestMapping(value = "/getOrder", method = RequestMethod.GET)
    OrderModel getOrder(@RequestParam String token, @RequestParam String orderCode) {
        return orderService.getSpecificOrder(token,orderCode);
    }

    @RequestMapping(value = "/updateSupplement", method = RequestMethod.POST)
    GenericResponse updateToSupplement(@RequestParam String token, @RequestParam String orderCode) {
        return orderService.updateToSupplement(token, orderCode);
    }
    @RequestMapping(value = "/finish", method = RequestMethod.POST)
    GenericResponse finishOrder(@RequestParam String token, @RequestParam String orderCode) {
        return orderService.finishOrder(token, orderCode);
    }
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    GenericResponse cancelOrder(@RequestParam String token, @RequestParam String orderCode) {
        return orderService.cancelOrder(token, orderCode);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    GenericResponse createOrder(@RequestParam String token, @RequestParam String orderCode) {
        return tsoftService.createOrder(token,orderCode);
    }

    @RequestMapping(value = "/getReturnedOrder", method = RequestMethod.GET)
    OrderLogSuccessModel getReturnedOrder(@RequestParam String token) {
        return orderService.getReturnedOrder(token);
    }
}
