package com.procsin.Retrofit.Models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceRequestModel {

    public int ModelType;
    public String OrderHeaderID;
    public String InvoiceDate;
    public boolean IsCompleted;
    public boolean SendInvoiceByEMail;
    public String EMailAddress;
    public List<OrderLineModel> OrderLineIDs;
    public List<PaymentRequestModel> Payments;

    public InvoiceRequestModel() {}
    public InvoiceRequestModel(int modelType, String orderHeaderID, String invoiceDate, boolean isCompleted, boolean sendInvoiceByEMail, List<OrderLineModel> orderLineIDs, List<PaymentRequestModel> payments) {
        ModelType = modelType;
        OrderHeaderID = orderHeaderID;
        InvoiceDate = invoiceDate;
        IsCompleted = isCompleted;
        SendInvoiceByEMail = sendInvoiceByEMail;
        OrderLineIDs = orderLineIDs;
        Payments = payments;
    }

    public InvoiceRequestModel(List<OrderDetailsModel> models, boolean isTrendyol) {
        if (models != null && models.size() > 0) {
            ModelType = 0;
            OrderHeaderID = models.get(0).OrderHeaderID;
            InvoiceDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            IsCompleted = true;
            SendInvoiceByEMail = true;

            if (isTrendyol) {
                EMailAddress = "trendyol-customer@procsin.com";
            }

            OrderLineIDs = new ArrayList<>();
            for (OrderDetailsModel model : models) {
                OrderLineIDs.add(new OrderLineModel(model.orderline));
            }

            Payments = new ArrayList<>();
            Payments.add(new PaymentRequestModel(models.get(0)));
        }
    }

}
