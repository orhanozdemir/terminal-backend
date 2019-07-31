package com.procsin.API.Service.Implementation;

import com.procsin.API.DAO.ShopDao;
import com.procsin.API.Service.Interface.ShopService;
import com.procsin.DB.Entity.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopRepository;

    @Override
    public List<Shop> shopList() {
        return shopRepository.findAllByIsActive(true);
    }

}
