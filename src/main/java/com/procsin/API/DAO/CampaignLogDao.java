package com.procsin.API.DAO;

import com.procsin.DB.Entity.Pack.CampaignLog;
import org.springframework.data.repository.CrudRepository;

public interface CampaignLogDao extends CrudRepository<CampaignLog, Long> {
}
