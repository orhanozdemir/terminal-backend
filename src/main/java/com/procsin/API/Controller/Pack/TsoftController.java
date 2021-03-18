package com.procsin.API.Controller.Pack;

import com.procsin.API.Model.GenericResponse;
import com.procsin.API.Model.OrderLogSuccessModel;
import com.procsin.API.Model.ResponseModel.MNGBarcodeResponseModel;
import com.procsin.API.Service.Interface.Pack.MNGService;
import com.procsin.API.Service.Interface.Pack.OrderService;
import com.procsin.API.Service.Interface.Pack.ReturnedOrderService;
import com.procsin.API.Service.Interface.Pack.TsoftService;
import com.procsin.DB.Entity.Pack.Return.ReturnedOrder;
import com.procsin.DB.Entity.Pack.Return.ReturnedOrderStatus;
import com.procsin.Retrofit.Models.TSoft.OrderModel;
import com.procsin.Retrofit.Models.TSoft.ProductModel;
import com.procsin.Retrofit.Models.TSoft.StatusEnum;
import com.procsin.Retrofit.Models.UpdateOrderStatusRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Query;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/tsoft")
public class TsoftController {

    @Autowired
    TsoftService tsoftService;

    @Autowired
    OrderService orderService;

    @Autowired
    ReturnedOrderService returnedOrderService;

    @Autowired
    MNGService mngService;

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    OrderLogSuccessModel getOrder(@RequestParam(required = false) String token, @RequestParam boolean isTrendyol) {
        return orderService.getSingleOrder(token, isTrendyol);
    }

    @RequestMapping(value = "/getOrder", method = RequestMethod.GET)
    OrderModel getOrder(@RequestParam(required = false) String token, @RequestParam String orderCode) {
        return orderService.getSpecificOrder(token, orderCode);
    }

    @RequestMapping(value = "/searchOrder", method = RequestMethod.GET)
    OrderModel searchOrder(@RequestParam(required = false) String token, @RequestParam String orderCode) throws IOException {
        return orderService.searchOrder(token, orderCode);
    }

    @RequestMapping(value = "/updateSupplement", method = RequestMethod.POST)
    GenericResponse updateToSupplement(@RequestParam(required = false) String token, @RequestParam(required = false) boolean isReturn, @RequestParam String orderCode) {
        return orderService.updateToSupplement(token, isReturn, orderCode);
    }
    @RequestMapping(value = "/finish", method = RequestMethod.POST)
    GenericResponse finishOrder(@RequestParam(required = false) String token, @RequestParam(required = false) boolean isReturn, @RequestParam String orderCode) {
        return orderService.finishOrder(token, isReturn, orderCode);
    }
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    GenericResponse cancelOrder(@RequestParam(required = false) String token, @RequestParam(required = false) boolean isReturn, @RequestParam String orderCode) {
        return orderService.cancelOrder(token,isReturn,orderCode);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    GenericResponse createOrder(@RequestParam(required = false) String token, @RequestParam String orderCode) {
        return tsoftService.createOrder(token,orderCode);
    }

    @RequestMapping(value = "/createAllWaitingOrders", method = RequestMethod.POST)
    GenericResponse createAllWaitingOrders(@RequestParam(required = false) String token) {
        List<ReturnedOrder> returnedOrders = returnedOrderService.returnedOrdersByStatus(ReturnedOrderStatus.YENIDEN_CIKIS_BEKLENIYOR);
        for (ReturnedOrder returnedOrder : returnedOrders) {
            if (!returnedOrder.newOrderCreated) {
                tsoftService.createOrder(token,returnedOrder.order.orderCode);
            }
        }
        return new GenericResponse(true,"Başarılı");
    }

    @RequestMapping(value = "/getReturnedOrder", method = RequestMethod.GET)
    OrderLogSuccessModel getReturnedOrder(@RequestParam(required = false) String token) {
        return orderService.getReturnedOrder(token);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "/allOrdersByStatus", method = RequestMethod.GET)
    List<OrderModel> getAllOrdersByStatus(@RequestParam StatusEnum status) throws IOException {
        return tsoftService.getAllOrdersByStatus(status);
    }

    @RequestMapping(value = "/updateOrderStatuses", method = RequestMethod.POST)
    GenericResponse updateOrderStatuses(@RequestBody UpdateOrderStatusRequestModel requestModel) throws IOException {
        List<OrderModel> allOrders = tsoftService.getAllOrdersByStatus(requestModel.fromStatus);
        List<OrderModel> filteredOrders = tsoftService.filterOrdersByProducts(allOrders, requestModel.withProducts, requestModel.withoutProducts);
        return tsoftService.updateStatuses(filteredOrders, requestModel.toStatus);
    }

    @RequestMapping(value = "/getOrderProductQuantities", method = RequestMethod.GET)
    public void getOrderProductQuantities(@RequestParam String start, @RequestParam String end) throws IOException {
        List<OrderModel> orders = tsoftService.getAllOrdersBetweenDates(start, end);
        tsoftService.productQuantitiesInOrders(orders);
    }

    @RequestMapping(value = "/getBasketCount", method = RequestMethod.GET)
    public void getBasketCount(@RequestParam String start, @RequestParam String end) throws IOException {
        List<OrderModel> orders = tsoftService.getAllOrdersBetweenDates(start, end);
        tsoftService.calculateBasketCount(orders);
    }

    @RequestMapping(value = "/getMNGBarcode", method = RequestMethod.GET)
    MNGBarcodeResponseModel trySth(@Query("orderCode") String orderCode) {
        return mngService.getMNGBarcode(orderCode);
    }
}
