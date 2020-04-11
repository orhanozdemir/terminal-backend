package com.procsin.API.Service.Implementation;

import com.procsin.API.Model.ItemBarcodeResponseModel;
import com.procsin.API.Service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    EntityManager em;

    @Override
    public List<ItemBarcodeResponseModel> getItemBarcodeList() {
        String query = "SELECT id = NEWID(), cdItem.ItemCode, Barcode FROM PROCSIN_V3.dbo.cdItem\n" +
                "LEFT JOIN PROCSIN_V3.dbo.prItemBarcode ON cdItem.ItemTypeCode = prItemBarcode.ItemTypeCode and cdItem.ItemCode = prItemBarcode.ItemCode\n" +
                "WHERE cdItem.ItemTypeCode = 1 AND UseInternet = 1";

        List<ItemBarcodeResponseModel> itemBarcodeList = em.createNativeQuery(query,ItemBarcodeResponseModel.class).getResultList();
        return itemBarcodeList;
    }
}
