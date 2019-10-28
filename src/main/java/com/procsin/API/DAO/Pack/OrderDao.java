package com.procsin.API.DAO.Pack;

import com.procsin.DB.Entity.Pack.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderDao extends CrudRepository<Orders, Long> {

    Orders findByOrderCode(String orderCode);

    @Query(value = "SELECT TOP 10 * FROM PRS_SEVK.sevk.Orders WHERE orderCode LIKE %:orderCode%", nativeQuery = true)
    List<Orders> findLikeOrderCode(@Param("orderCode") String orderCode);

    @Query(value = "SELECT TOP 100 * FROM PRS_SEVK.sevk.Orders ORDER BY orderDate DESC", nativeQuery = true)
    List<Orders> findLastHundredOrders();

    @Query(value = "SELECT TOP 300 * FROM PRS_SEVK.sevk.Orders WHERE orderDate BETWEEN :fromDate AND :toDate ORDER BY orderDate DESC", nativeQuery = true)
    List<Orders> findOrdersByDate(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

    @Query(value = "SELECT * FROM PRS_SEVK.sevk.Orders WHERE orderDate IS NULL", nativeQuery = true)
    List<Orders> findOrdersWithoutDate();

    @Query(value = "SELECT * FROM PRS_SEVK.sevk.Orders WHERE invoiceRefNumber IS NULL", nativeQuery = true)
    List<Orders> findOrdersWithoutInvoiceRefNumber();

}
