package com.procsin.API.Service.Interface.Count;

import com.procsin.DB.Entity.Count.Quantity;

public interface QuantityService {

    Quantity changeQuantity(Long userId, Long quantityId, int count);
    Quantity countShelf(Long userId, Quantity quantity);

}
