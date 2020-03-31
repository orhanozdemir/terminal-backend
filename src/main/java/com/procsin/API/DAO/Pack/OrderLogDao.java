package com.procsin.API.DAO.Pack;

import com.procsin.API.Model.LeadershipResponseEntity;
import com.procsin.DB.Entity.Pack.OrderLog;
import com.procsin.DB.Entity.Pack.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLogDao extends CrudRepository<OrderLog,Long> {

    @Query(value = "SELECT u.username, COUNT(*) as 'totalOrderCount', SUM(total_cost) as 'totalCost', SUM(total_product_count) as 'totalProductCount' FROM order_log o, user u\n" +
            "WHERE u.id = o.user_id AND o.status = '3'\n" +
            "GROUP BY u.username", nativeQuery = true)
    List<LeadershipResponseEntity> getLeadershipData();

    List<OrderLog> findAllByStatus(OrderLog.OrderStatus status);

    @Query(value = "SELECT TOP 20 * FROM PRS_SEVK.sevk.OrderLog WHERE (status = 1 OR status = 2) AND user_id = :userId ORDER BY date DESC", nativeQuery = true)
    List<OrderLog> findPreviousOrderLogs(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM PRS_SEVK.sevk.OrderLog WHERE status = 1 AND user_id = :userId", nativeQuery = true)
    List<OrderLog> findMyStats(@Param("userId") Long userId);

    List<OrderLog> findAllByOrder(Orders order);

    @Query(value = "SELECT OrderLog.* FROM PRS_SEVK.sevk.OrderLog LEFT JOIN PRS_SEVK.sevk.Orders ON OrderLog.order_id = Orders.id WHERE orderCode = :orderCode", nativeQuery = true)
    List<OrderLog> findAllByOrderCode(@Param("orderCode") String orderCode);

    @Query(value = "SELECT * FROM PRS_SEVK.sevk.OrderLog WHERE order_id = :orderId ORDER BY date DESC", nativeQuery = true)
    List<OrderLog> getOrderLogs(@Param("orderId") Long orderId);

    @Query(value = "SELECT TOP 1 o.* FROM PRS_SEVK.sevk.OrderLog o, PRS_SEVK.sevk.Orders a WHERE o.order_id = a.id and o.status = 1 and a.orderCode = :orderCode", nativeQuery = true)
    OrderLog findReadyByOrderCode(@Param("orderCode") String orderCode);

    //STATS
    @Query(value = "SELECT TOP 1 * FROM PRS_SEVK.sevk.OrderLog WHERE user_id = :userId AND order_id = :orderId AND status = 0 ORDER BY id DESC", nativeQuery = true)
    OrderLog findStartLog(@Param("orderId") Long orderId, @Param("userId") Long userId);

    @Query(value = "SELECT a.* FROM PRS_SEVK.sevk.OrderLog a, PRS_SEVK.sevk.Orders b WHERE a.order_id = b.id AND b.orderCode = :orderCode", nativeQuery = true)
    List<OrderLog> getOrderDetails(@Param("orderCode") String orderCode);

    @Query(value = "UPDATE PRS_SEVK.sevk.Orders SET status = :status WHERE orderCode = :orderCode", nativeQuery = true)
    void updateOrderStatus(@Param("status") String status, @Param("orderCode") String orderCode);
}
