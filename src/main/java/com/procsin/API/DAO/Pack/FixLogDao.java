package com.procsin.API.DAO.Pack;

import com.procsin.DB.Entity.Pack.FixLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixLogDao extends CrudRepository<FixLog, Long> {

}
