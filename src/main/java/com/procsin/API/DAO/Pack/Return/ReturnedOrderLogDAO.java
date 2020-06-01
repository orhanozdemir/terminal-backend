package com.procsin.API.DAO.Pack.Return;

import com.procsin.DB.Entity.Pack.Return.ReturnedOrderLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnedOrderLogDAO extends CrudRepository<ReturnedOrderLog, Long> {
    
}
