package com.procsin.API.Service.Implementation;

import com.procsin.API.DAO.ProductDao;
import com.procsin.API.Service.Interface.ProductService;
import com.procsin.DB.Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productRepository;

    @Autowired
    public List<Product> getProductList() {
//        return productRepository.findAll();
        return null;
    }
}
