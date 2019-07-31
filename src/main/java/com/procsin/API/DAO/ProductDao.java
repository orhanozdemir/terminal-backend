package com.procsin.API.DAO;

import com.procsin.DB.Entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends CrudRepository<Product,Long>{

}
