package com.procsin.API.DAO.Count;

import com.procsin.DB.Entity.Count.Quantity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuantityDao extends CrudRepository<Quantity,Long> {

    Optional<Quantity> findById(Long id);

    @Query(value = "SELECT q.id, barcode, count, date \n" +
            "FROM PRS_SEVK.sevk.Quantity q, PRS_SEVK.sevk.Enumeration e, PRS_SEVK.sevk.enumeration_quantity eq, PRS_SEVK.sevk.quantity_account qa\n" +
            "WHERE q.id = qa.quantity_id AND qa.account_id = :accountId AND eq.enumeration_id = :enumerationId", nativeQuery = true)
    List<Quantity> findQuantitiesByEnumerationAndAccount(@Param("enumerationId") String enumerationId, @Param("accountId") String accountId);

}
