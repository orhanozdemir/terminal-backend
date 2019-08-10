package com.procsin.Network;

import java.util.List;

public class ProductResponseModel {

    public String OrderProductId;
    public Integer PackageQuantity;
    public Integer Quantity;
    public String ProductCode;
    public String ProductName;
    public List<ProductResponseModel> PackageContent;
    public String Barcode;
    public String IsPackage;
    public String ImageUrlBig;

    public String Vat;
    public String DiscountedPrice;

    public Boolean isProcessed = false;

    public int count;

    public ProductResponseModel() { }

    public ProductResponseModel(String orderProductId, Integer packageQuantity, Integer quantity, String productCode,
                                String productName, List<ProductResponseModel> packageContent, String barcode, String isPackage,
                                String imageUrlBig, String vat, String discountedPrice, Boolean isProcessed) {
        OrderProductId = orderProductId;
        PackageQuantity = packageQuantity;
        Quantity = quantity;
        ProductCode = productCode;
        ProductName = productName;
        PackageContent = packageContent;
        Barcode = barcode;
        IsPackage = isPackage;
        ImageUrlBig = imageUrlBig;
        Vat = vat;
        DiscountedPrice = discountedPrice;
        this.isProcessed = isProcessed;
    }
}