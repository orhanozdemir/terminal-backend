package com.procsin.API.Service.Implementation.Count;

import com.procsin.API.DAO.Count.QuantityDao;
import com.procsin.API.DAO.UserDao;
import com.procsin.API.Service.Interface.Count.QuantityService;
import com.procsin.DB.Entity.Count.Quantity;
import com.procsin.DB.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class QuantityServiceImpl implements QuantityService {

    @Autowired
    private QuantityDao quantityRepository;
    @Autowired
    private UserDao userRepository;

    public Quantity changeQuantity(Long userId, Long quantityId, int count) {
        User user = userRepository.findById(userId).get();
        Quantity quantity = quantityRepository.findById(quantityId).get();
        quantity.setCount(count);
        quantity.setCreatedBy(user);
        quantity.setCreatedAt(new Date());
        return quantityRepository.save(quantity);
    }

    public Quantity countShelf(Long userId, Quantity quantity) {
        User user = userRepository.findById(userId).get();
        quantity.setCreatedBy(user);
        quantity.setCreatedAt(new Date());
        return quantityRepository.save(quantity);
    }

}
