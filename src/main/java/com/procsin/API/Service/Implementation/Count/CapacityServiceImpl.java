package com.procsin.API.Service.Implementation.Count;

import com.procsin.API.DAO.Count.CapacityDao;
import com.procsin.API.DAO.Count.EnumerationDao;
import com.procsin.API.DAO.Count.ShelfDao;
import com.procsin.API.DAO.UserDao;
import com.procsin.API.Model.AddCapacityRequestModel;
import com.procsin.API.Service.Interface.Count.CapacityService;
import com.procsin.DB.Entity.Count.Capacity;
import com.procsin.DB.Entity.Count.Enumeration;
import com.procsin.DB.Entity.Count.Shelf;
import com.procsin.DB.Entity.UserManagement.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class CapacityServiceImpl implements CapacityService {

    @Autowired
    CapacityDao capacityRepository;
    @Autowired
    UserDao userRepository;
    @Autowired
    EnumerationDao enumerationRepository;
    @Autowired
    ShelfDao shelfRepository;

    public Capacity setCapacity(Long userId, AddCapacityRequestModel requestModel) {
        User user = userRepository.findById(userId).get();
        Enumeration enumeration = enumerationRepository.findById(requestModel.enumId).get();
        Shelf shelf = shelfRepository.findById(requestModel.shelfId).get();
        Capacity model = new Capacity();

        model.setCreatedBy(user);
        model.setEnumeration(enumeration);
        model.setShelf(shelf);
        model.setCount(requestModel.capacity);
        model.setCreatedAt(new Date());

        return capacityRepository.save(model);
    }
}
