package com.procsin.API.DAO.Pack;

import com.procsin.DB.Entity.Pack.Orders;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends CrudRepository<Orders, Long> {

    Orders findByOrderCode(String orderCode);

}
