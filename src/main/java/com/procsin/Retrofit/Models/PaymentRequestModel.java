package com.procsin.Retrofit.Models;

public class PaymentRequestModel {

    public int PaymentType;
    public String Code;
    public String CreditCardTypeCode;
    public int InstallmentCount;
    public String CurrencyCode;
    public double Amount;

    public PaymentRequestModel() {}
    public PaymentRequestModel(int paymentType, String code, String creditCardTypeCode, int installmentCount, String currencyCode, double amount) {
        PaymentType = paymentType;
        Code = code;
        CreditCardTypeCode = creditCardTypeCode;
        InstallmentCount = installmentCount;
        CurrencyCode = currencyCode;
        Amount = amount;
    }
    public PaymentRequestModel(OrderDetailsModel model) {
        PaymentType = model.PaymentTypeCode;
        Code = "";
        CreditCardTypeCode = model.odeme;
        InstallmentCount = model.InstallmentCount;
        CurrencyCode = model.DocCurrencyCode;
        Amount = model.odemetutar;
    }

}
