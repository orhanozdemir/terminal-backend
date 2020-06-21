package com.procsin.Retrofit.Models.TSoft;

import com.google.gson.annotations.Expose;
import java.util.List;

public class ProductModel {
    public String OrderProductId;
    public Integer PackageQuantity;
    public Integer Quantity;
    public String ProductCode;
    public String ProductName;
    public List<ProductModel> PackageContent;
    public String Barcode;
    public String IsPackage;
    public String ImageUrlBig;
    public String Vat;
    public String DiscountedPrice;
    public int RefundCount;

    @Expose
    public Boolean isProcessed = false;
    @Expose
    public Integer count;

    @Expose
    private Integer totalCount;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public ProductModel() {}

    public ProductModel(SyncProductModel model) {
        this.ProductCode = model.ProductCode;
        this.ProductName = model.ProductName;
        this.Barcode= model.Barcode;
        this.Quantity = model.Quantity;
        boolean isPackage = (model.PackageProducts != null && model.PackageProducts.size() > 0);
        this.IsPackage = isPackage ? "1" : "0";
    }
}