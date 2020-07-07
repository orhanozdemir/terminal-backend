package com.procsin.API.DAO.Pack.Return;

import com.procsin.DB.Entity.Pack.Return.ReturnedProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnedProductDAO extends CrudRepository<ReturnedProduct, Long> {
}
