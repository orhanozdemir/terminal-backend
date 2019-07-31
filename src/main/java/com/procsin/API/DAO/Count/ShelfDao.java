package com.procsin.API.DAO.Count;

import com.procsin.DB.Entity.Count.Shelf;
import com.procsin.DB.Entity.Shop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShelfDao extends CrudRepository<Shelf,Long> {

    Optional<Shelf> findById(Long id);

    List<Shelf> findAllByShop(Shop shop);

}
