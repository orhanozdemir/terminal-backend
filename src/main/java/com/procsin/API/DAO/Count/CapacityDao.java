package com.procsin.API.DAO.Count;

import com.procsin.DB.Entity.Count.Capacity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CapacityDao extends CrudRepository<Capacity,Long> {

    @Query(value = "SELECT  * FROM PRS_SEVK.sevk.Capacity c, PRS_SEVK.sevk.capacity_shelf cs, PRS_SEVK.sevk.capacity_enumeration ce " +
            "WHERE cs.capacity_id = ce.capacity_id AND c.id = cs.capacity_id AND cs.shelf_id = :shelfId AND ce.enumeration_id = :enumId", nativeQuery = true)
    Capacity findByEnumerationAndShelf(@Param("enumId") Long enumId, @Param("shelfId") Long shelfId);

}
