package com.procsin.API.Service.Implementation.Count;

import com.procsin.API.DAO.Count.EnumerationDao;
import com.procsin.API.DAO.Count.ShelfDao;
import com.procsin.API.DAO.UserDao;
import com.procsin.API.Service.Interface.Count.EnumerationService;
import com.procsin.DB.Entity.Count.Enumeration;
import com.procsin.DB.Entity.UserManagement.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EnumerationServiceImpl implements EnumerationService {

    @Autowired
    private EnumerationDao enumerationRepository;

    @Autowired
    private ShelfDao shelfRepository;

    @Autowired
    private UserDao userRepository;

    public Enumeration create(Long userId, Enumeration enumeration) {
        User user = userRepository.findById(userId).get();
        enumeration.setCreatedBy(user);
        enumeration.setCreatedAt(new Date());
        return enumerationRepository.save(enumeration);
    }

    public Enumeration finish(Long userId, Long enumerationId) {
        User user = userRepository.findById(userId).get();
        Enumeration enumeration = enumerationRepository.findById(enumerationId).get();
        enumeration.setStatus(Enumeration.EnumStatus.FINISHED);
        enumeration.setUpdatedBy(user);
        enumeration.setUpdatedAt(new Date());
        enumerationRepository.save(enumeration);
        return enumeration;
    }

    public Enumeration cancel(Long userId, Long enumerationId) {
        User user = userRepository.findById(userId).get();
        Enumeration enumeration = enumerationRepository.findById(enumerationId).get();
        enumeration.setStatus(Enumeration.EnumStatus.CANCELLED);
        enumeration.setUpdatedBy(user);
        enumeration.setUpdatedAt(new Date());
        return enumerationRepository.save(enumeration);
    }

//    public Shelf addShelfToEnum(Shelf shelf) {
//        return shelfRepository.save(shelf);
//    }

//    public Enumeration addShelvesToEnum(Long userId, AddShelvesRequestModel model) {
//        Enumeration enumeration = enumerationRepository.findById(model.enumId);
//        List<Shelf> shelfList = enumeration.getShelves();
//        if (shelfList == null) {
//            shelfList = new ArrayList<Shelf>();
//        }
//        for (int i = 0; i < model.count; i++) {
//            int code = 0;
//            if (shelfList.size() > 0) {
//                code = shelfList.get(shelfList.size() - 1).getShelfCode() + 1;
//            }
//            Shelf shelf = new Shelf();
//            shelf.setShelfCode(code);
//            shelf.setShelfBarcode(String.format("%04d",model.enumId) + String.format("%04d",code));
//            shelfRepository.save(shelf);
//            shelfList.add(shelf);
//        }
//        enumeration.setShelves(shelfList);
//        return enumerationRepository.save(enumeration);
//    }

    public List<Enumeration> activeEnumerations() {
        return enumerationRepository.findAllByStatus(Enumeration.EnumStatus.ACTIVE);
    }
}
