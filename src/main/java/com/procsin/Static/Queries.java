package com.procsin.Static;

public class Queries {

    public static String LEADERSHIP_DAILY_QUERY = "SELECT NEWID() as id, username as username, SUM(totalProductCount) as productCount, SUM(totalCost) as cost, COUNT(*) as orderCount "+
            "FROM PRS_SEVK.sevk.OrderLog, PRS_SEVK.sevk.Account, PRS_SEVK.sevk.Orders "+
            "WHERE order_id = Orders.id AND status = 1 AND user_id = Account.id AND date BETWEEN convert(date, GETDATE()) AND DATEADD(day, 1, convert(date, GETDATE())) "+
            "GROUP BY username ORDER BY orderCount DESC";
    public static String LEADERSHIP_MONTHLY_QUERY = "SELECT NEWID() as id, username as username, SUM(totalProductCount) as productCount, SUM(totalCost) as cost, COUNT(*) as orderCount "+
            "FROM PRS_SEVK.sevk.OrderLog, PRS_SEVK.sevk.Account, PRS_SEVK.sevk.Orders " +
            "WHERE order_id = Orders.id AND status = 1 AND user_id = Account.id AND date between (SELECT DATEADD(mm, DATEDIFF(mm, 0, GETDATE()), 0)) AND (DATEADD(day, 1, convert(date, GETDATE()))) " +
            "GROUP BY username ORDER BY orderCount DESC";
    public static String LEADERSHIP_ALL_QUERY = "SELECT NEWID() as id, username as username, SUM(totalProductCount) as productCount, SUM(totalCost) as cost, COUNT(*) as orderCount " +
            "FROM PRS_SEVK.sevk.OrderLog, PRS_SEVK.sevk.Account, PRS_SEVK.sevk.Orders " +
            "WHERE order_id = Orders.id AND status = 1 AND user_id = Account.id " +
            "GROUP BY username ORDER BY orderCount DESC";
    public static String STATS_MY_ALL_STATS = "SELECT NEWID() as id, username, SUM(totalProductCount) as totalProductCount, SUM(totalCost) as totalCost, COUNT(*) as totalOrderCount " +
            "FROM PRS_SEVK.sevk.OrderLog, PRS_SEVK.sevk.Account, PRS_SEVK.sevk.Orders " +
            "WHERE order_id = Orders.id AND status = 1 AND user_id = Account.id AND user_id = :userId " +
            "GROUP BY username";
    public static String STATS_MY_DAILY_STATS = "SELECT NEWID() as id, username, SUM(totalProductCount) as totalProductCount, SUM(totalCost) as totalCost, COUNT(*) as totalOrderCount " +
            "FROM PRS_SEVK.sevk.OrderLog, PRS_SEVK.sevk.Account, PRS_SEVK.sevk.Orders " +
            "WHERE order_id = Orders.id AND status = 1 AND user_id = Account.id AND user_id = :userId " +
            "AND date BETWEEN convert(date, GETDATE()) AND DATEADD(day, 1, convert(date, GETDATE())) " +
            "GROUP BY username";
}
