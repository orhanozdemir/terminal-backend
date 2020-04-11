package com.procsin.API.Controller;

import com.procsin.API.Model.ItemBarcodeResponseModel;
import com.procsin.API.Service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/barcodeList", method = RequestMethod.GET)
    List<ItemBarcodeResponseModel> getOrder() {
        return productService.getItemBarcodeList();
    }

}
