package com.procsin.API.DAO.Pack.Return;

import com.procsin.DB.Entity.Pack.Return.CreatedOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatedOrderDAO extends CrudRepository<CreatedOrder, Long> {
    CreatedOrder findByNewOrderCode(String newOrderCode);
}
