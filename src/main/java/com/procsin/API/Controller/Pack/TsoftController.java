package com.procsin.API.Controller.Pack;

import com.procsin.API.Model.GenericResponse;
import com.procsin.API.Model.OrderLogSuccessModel;
import com.procsin.API.Service.Interface.Pack.TsoftService;
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

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    OrderLogSuccessModel getOrder(@RequestParam String token, @RequestParam boolean isTrendyol) {
        return tsoftService.getSingleOrder(token, isTrendyol);
    }
    @RequestMapping(value = "/updateSupplement", method = RequestMethod.POST)
    GenericResponse updateToSupplement(@RequestParam String token, @RequestParam String orderCode) {
        return tsoftService.updateToSupplement(token, orderCode);
    }
    @RequestMapping(value = "/finish", method = RequestMethod.POST)
    GenericResponse finishOrder(@RequestParam String token, @RequestParam String orderCode) {
        return tsoftService.finishOrder(token, orderCode);
    }
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    GenericResponse cancelOrder(@RequestParam String token, @RequestParam String orderCode) {
        return tsoftService.cancelOrder(token, orderCode);
    }

}
