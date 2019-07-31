package com.procsin.API.Service.Interface;

import com.procsin.DB.Entity.User;

import java.util.List;

public interface UserService {

    User save(User user);
    List<User> findAll();
    void delete(long id);
    User findOne(String username);

    User findById(Long id);

}
