package com.procsin.API.DAO.Pack.Return;

import com.procsin.DB.Entity.Pack.Return.ReturnedOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReturnedOrderDAO extends CrudRepository<ReturnedOrder,Long> {
    List<ReturnedOrder> findAllByStatus(ReturnedOrder.ReturnedOrderStatus status);
}
