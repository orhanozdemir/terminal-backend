package com.procsin.API.DAO.Count;

import com.procsin.DB.Entity.Count.Enumeration;
import com.procsin.DB.Entity.Shop;
import com.procsin.DB.Entity.UserManagement.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnumerationDao extends CrudRepository<Enumeration,Long> {

    List<Enumeration> findAll();
    List<Enumeration> findAllByCreatedAt(Date createdAt);
    List<Enumeration> findAllByShop(Shop shop);
    List<Enumeration> findAllByCreatedBy(User user);
    List<Enumeration> findAllByStatus(Enumeration.EnumStatus status);

    Optional<Enumeration> findById(Long id);

}
