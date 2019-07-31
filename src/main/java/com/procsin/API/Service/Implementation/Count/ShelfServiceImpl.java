package com.procsin.API.Service.Implementation.Count;

import com.procsin.API.DAO.Count.EnumerationDao;
import com.procsin.API.DAO.Count.ShelfDao;
import com.procsin.API.DAO.ShopDao;
import com.procsin.API.DAO.UserDao;
import com.procsin.API.Model.AddShelvesRequestModel;
import com.procsin.API.Service.Interface.Count.ShelfService;
import com.procsin.DB.Entity.Count.Shelf;
import com.procsin.DB.Entity.Shop;
import com.procsin.DB.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ShelfServiceImpl implements ShelfService {

    @Autowired
    private EnumerationDao enumerationRepository;
    @Autowired
    private ShelfDao shelfRepository;
    @Autowired
    private UserDao userRepository;
    @Autowired
    private ShopDao shopRepository;

    public List<Shelf> addShelves(Long userId, AddShelvesRequestModel model) {
        Shop shop = shopRepository.findById(model.shopId).get();
        User user = userRepository.findById(userId).get();

        for (int i = 1; i < model.sectionCount + 1; i++) {
            for (int k = 1; k < model.rowPerSectionCount + 1; k++) {
                Shelf shelf = new Shelf();
                shelf.setShop(shop);
                shelf.setSection(i);
                shelf.setRow(k);
                shelf.setType(model.type);
                shelf.setActive(true);
                shelf.setCreatedBy(user);
                shelf.setCreatedAt(new Date());

                String shelfName = generateShelfName(shelf);
                shelf.setName(shelfName);

                String shelfBarcode = generateShelfBarcode(shelf);
                shelf.setBarcode(shelfBarcode);

                shelfRepository.save(shelf);
            }
        }
        return getShelvesForShop(shop.getId());
    }

    public List<Shelf> getShelvesForShop(Long shopId) {
        Shop shop = shopRepository.findById(shopId).get();
        return shelfRepository.findAllByShop(shop);
    }

    public String generateShelfName(Shelf shelf) {
        int prefixNumber = shelf.getSection() + 64;
        char sectionPrefix = (char) prefixNumber;
        return sectionPrefix + '-' + String.valueOf(shelf.getRow());
    }

    public String generateShelfBarcode(Shelf shelf) {
        String shopPart = String.format("%03d", shelf.getShop().getId());
        String typePart = String.format("%01d", getTypeCode(Shelf.ShelfType.valueOf(shelf.getType())));
        String sectionPart = String.format("%02d", shelf.getSection());
        String rowPart = String.format("%02d", shelf.getRow());
        return shopPart + typePart + sectionPart + rowPart;
    }

    private int getTypeCode(Shelf.ShelfType type) {
        if (type.equals(Shelf.ShelfType.RAF)) {
            return 1;
        }
        else if (type.equals(Shelf.ShelfType.STAND)) {
            return 2;
        }
        else if (type.equals(Shelf.ShelfType.DEPO)) {
            return 3;
        }

        return -1;
    }

    public Shelf editShelf(Shelf shelf) {
        return shelf;
    }

}
