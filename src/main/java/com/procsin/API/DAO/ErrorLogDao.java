package com.procsin.API.DAO;

import com.procsin.DB.Entity.ErrorLog;
import org.springframework.data.repository.CrudRepository;

public interface ErrorLogDao extends CrudRepository<ErrorLog, Long> {

}
