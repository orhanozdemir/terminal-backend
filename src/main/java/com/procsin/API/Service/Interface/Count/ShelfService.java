package com.procsin.API.Service.Interface.Count;

import com.procsin.API.Model.AddShelvesRequestModel;
import com.procsin.DB.Entity.Count.Shelf;

import java.util.List;

public interface ShelfService {

    List<Shelf> addShelves(Long userId, AddShelvesRequestModel model);
    List<Shelf> getShelvesForShop(Long shopId);
    String generateShelfName(Shelf shelf);
    String generateShelfBarcode(Shelf shelf);
    Shelf editShelf(Shelf shelf);

}
