package com.procsin.API.Controller.Pack;

import com.procsin.API.Model.PrintingApp.PrintOrderModel;
import com.procsin.Static.Queries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.persistence.EntityManager;
import java.util.List;

@RequestMapping(value = "/print")
@RestController
public class PrintController {

    @Autowired
    EntityManager em;

    @RequestMapping(value = "/non-printed-orders", method = RequestMethod.GET)
    List<PrintOrderModel> getNonPrintedOrders() {
        List<PrintOrderModel> models = em.createNativeQuery(Queries.GET_NON_PRINTED_ORDERS,PrintOrderModel.class).getResultList();
        return models;
    }


}
