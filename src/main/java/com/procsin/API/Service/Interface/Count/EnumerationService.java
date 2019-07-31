package com.procsin.API.Service.Interface.Count;

import com.procsin.DB.Entity.Count.Enumeration;
import java.util.List;

public interface EnumerationService {

    Enumeration create(Long userId, Enumeration enumeration);
    Enumeration finish(Long userId, Long enumerationId);
    Enumeration cancel(Long userId, Long enumerationId);
    List<Enumeration> activeEnumerations();

}
