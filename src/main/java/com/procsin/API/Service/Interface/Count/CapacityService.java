package com.procsin.API.Service.Interface.Count;

import com.procsin.API.Model.AddCapacityRequestModel;
import com.procsin.DB.Entity.Count.Capacity;

public interface CapacityService {

    Capacity setCapacity(Long userId, AddCapacityRequestModel requestModel);

}
