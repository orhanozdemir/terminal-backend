package com.procsin.API.DAO;

import com.procsin.DB.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User,Long> {

    User findByUsername(String username);

}
