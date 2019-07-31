package com.procsin.API.DAO.Pack;

import com.procsin.DB.Entity.Pack.Campaign;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignDao extends CrudRepository<Campaign, Long> {

    @Query(value = "SELECT TOP 1 * FROM PRS_SEVK.sevk.Campaign WHERE costThreshold = (SELECT MAX(costThreshold) FROM PRS_SEVK.sevk.Campaign WHERE isActive = 1 AND costThreshold <= :cost) AND isActive = 1 AND costThreshold <= :cost", nativeQuery = true)
    Campaign findSuitableCampaign(@Param("cost") double cost);

}
