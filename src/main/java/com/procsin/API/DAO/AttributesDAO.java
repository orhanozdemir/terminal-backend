package com.procsin.API.DAO;

import com.procsin.DB.Entity.Attributes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributesDAO extends CrudRepository<Attributes,Long> {
    Attributes getByKeyString(String keyString);
}

