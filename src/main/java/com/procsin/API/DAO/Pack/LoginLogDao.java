package com.procsin.API.DAO.Pack;

import com.procsin.DB.Entity.Pack.LoginLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginLogDao extends CrudRepository<LoginLog,Long> {



}
