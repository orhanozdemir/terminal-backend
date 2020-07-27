package com.procsin.API.DAO.Pack.Return;

import com.procsin.DB.Entity.Pack.Return.PRSOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDAO extends CrudRepository<PRSOrder,Long> {
    PRSOrder findByOrderCode(String orderCode);
}
