package com.procsin.API.Controller.Count;

import com.procsin.API.Model.AddCapacityRequestModel;
import com.procsin.API.Model.AddShelvesRequestModel;
import com.procsin.API.Service.Interface.Count.CapacityService;
import com.procsin.API.Service.Interface.Count.EnumerationService;
import com.procsin.API.Service.Interface.Count.QuantityService;
import com.procsin.API.Service.Interface.Count.ShelfService;
import com.procsin.DB.Entity.Count.Capacity;
import com.procsin.DB.Entity.Count.Enumeration;
import com.procsin.DB.Entity.Count.Quantity;
import com.procsin.DB.Entity.Count.Shelf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EnumerationController {

    @Autowired
    private EnumerationService enumerationService;

    @Autowired
    private ShelfService shelfService;
    @Autowired
    private QuantityService quantityService;
    @Autowired
    private CapacityService capacityService;

    @RequestMapping(value = "/enumeration/activeList", method = RequestMethod.GET)
    public List<Enumeration> activeList() {
        return enumerationService.activeEnumerations();
    }

    @RequestMapping(value = "/enumeration/create", method = RequestMethod.POST)
    public Enumeration create(@RequestHeader Long userId, @RequestBody Enumeration enumeration) {
        return enumerationService.create(userId, enumeration);
    }

    @RequestMapping(value = "/enumeration/finish", method = RequestMethod.POST)
    public Enumeration finish(@RequestHeader Long userId, @RequestParam Long enumerationId) {
        return enumerationService.finish(userId,enumerationId);
    }

    @RequestMapping(value = "/enumeration/cancel", method = RequestMethod.POST)
    public Enumeration cancel(@RequestHeader Long userId, @RequestParam Long enumerationId) {
        return enumerationService.cancel(userId,enumerationId);
    }

//    @RequestMapping(value = "/enumeration/addShelf", method = RequestMethod.POST)
//    public Enumeration addShelfToEnum(@RequestParam Long enumId, @RequestBody Shelf shelf) {
//        return enumerationService.addShelfToEnum(enumId,shelf);
//    }

    @RequestMapping(value = "/shop/addShelves", method = RequestMethod.POST)
    public List<Shelf> addShelvesToShop(@RequestHeader Long userId, @RequestBody AddShelvesRequestModel model) {
        return shelfService.addShelves(userId,model);
    }

    @RequestMapping(value = "/shop/getShelves", method = RequestMethod.GET)
    public List<Shelf> getShelves(Long shopId) {
        return shelfService.getShelvesForShop(shopId);
    }

    @RequestMapping(value = "/capacity/add", method = RequestMethod.POST)
    public Capacity setShelfCapacity(@RequestHeader Long userId, @RequestBody AddCapacityRequestModel model) {
        return capacityService.setCapacity(userId,model);
    }

    @RequestMapping(value = "/shelf/count", method = RequestMethod.POST)
    public Quantity countShelf(@RequestHeader Long userId, @RequestBody Quantity quantity) {
        return quantityService.countShelf(userId, quantity);
    }

    @RequestMapping(value = "/quantity/edit", method = RequestMethod.POST)
    public Quantity changeQuantity(@RequestHeader Long userId, @RequestParam Long quantityId, @RequestBody int count) {
        return quantityService.changeQuantity(userId, quantityId, count);
    }

}
