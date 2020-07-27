package com.procsin.API.DAO.Pack.Return;

import com.procsin.DB.Entity.Pack.Return.PRSOrder;
import com.procsin.DB.Entity.Pack.Return.ReturnedOrder;
import com.procsin.DB.Entity.Pack.Return.ReturnedOrderStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReturnedOrderDAO extends CrudRepository<ReturnedOrder,Long> {
    ReturnedOrder findByOrderAndIsCompleted(PRSOrder order, Boolean isCompleted);
    List<ReturnedOrder> findAllByStatus(ReturnedOrderStatus status);
}
