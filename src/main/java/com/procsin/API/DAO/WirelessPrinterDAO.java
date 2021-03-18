package com.procsin.API.DAO;

import com.procsin.DB.Entity.WirelessPrinter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WirelessPrinterDAO extends CrudRepository<WirelessPrinter, Long> {
}
