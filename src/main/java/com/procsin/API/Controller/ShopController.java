package com.procsin.API.Controller;

import com.procsin.API.Service.Implementation.ShopServiceImpl;
import com.procsin.DB.Entity.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopServiceImpl shopService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Shop> getShopList() {
        return shopService.shopList();
    }

}