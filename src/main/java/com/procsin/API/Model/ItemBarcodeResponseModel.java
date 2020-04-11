package com.procsin.API.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ItemBarcode", schema = "responses", catalog = "PRS_SEVK")
public class ItemBarcodeResponseModel {

    @Id
    public String id;

    public String itemCode;
    public String barcode;

    public ItemBarcodeResponseModel() {}
}
