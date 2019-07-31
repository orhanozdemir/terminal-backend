package com.procsin.API.DAO;

import com.procsin.DB.Entity.Shop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopDao extends CrudRepository<Shop, Long> {

    List<Shop> findAllByIsActive(Boolean isActive);

}
