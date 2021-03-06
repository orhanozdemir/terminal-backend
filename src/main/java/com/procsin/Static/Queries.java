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

    public static String GET_FAILED_ORDERS = "SELECT TOP 20 a.* FROM PRS_SEVK.sevk.Orders a, PRS_SEVK.sevk.OrderLog b, PRS_SEVK.sevk.Account c\n" +
            "WHERE b.user_id = c.id AND a.id = b.order_id AND b.status = 1 AND a.didFail = 1\n" +
            "ORDER BY orderDate DESC";
    public static String GET_FAIL_STATS = "SELECT * FROM " +
            "(SELECT COUNT(*) as 'failCount' FROM PRS_SEVK.sevk.Orders WHERE didFail = 1) as a, " +
            "(SELECT COUNT(*) as 'orderCount' FROM PRS_SEVK.sevk.Orders) as b";
    public static String GET_FAIL_USER_STATS = "";

//    public static String CHANGE_FAILED_STATUS = "UPDATE PRS_SEVK.sevk.Orders SET didFail = :didFail WHERE orderCode = :orderCode";
    public static String ORDERS_BY_DATE = "SELECT NEWID() as id, a.deliveryName, a.orderCode, a.totalCost, a.totalProductCount, c.username, b.date FROM PRS_SEVK.sevk.Orders a, PRS_SEVK.sevk.OrderLog b, PRS_SEVK.sevk.Account c\n" +
            "WHERE b.user_id = c.id AND a.id = b.order_id AND b.status = 1 AND b.date BETWEEN :fromDate AND :toDate";

    public static String PRODUCT_LIST = "SELECT a.ItemCode as 'productCode', b.ItemDescription as 'name' FROM PRS_MAGAZACILIK.dbo.cdItem a\n" +
            "LEFT JOIN PRS_MAGAZACILIK.dbo.cdItemDesc b ON a.ItemCode = b.ItemCode\n" +
            "WHERE a.ItemCode NOT LIKE 'Z%' AND a.ItemCode NOT LIKE 'SA.94%' AND a.ItemCode != 'DV'\n" +
            "  AND a.UseStore = 1 AND a.ItemTypeCode = 1";

    public static String GET_ONLINE_ORDER = "USE PROCSIN_V3\n" +
            "EXEC usp_Getsiparis :orderCode";

    public static String GET_INVOICE_DETAILS = "SELECT TOP 1 NEWID() as id, InternalDescription, InvoiceNumber, EInvoiceNumber FROM PROCSIN_V3.dbo.AllInvoices WHERE InternalDescription = :InternalDescription";

    public static String GET_RETAIL_CUSTOMER = "SELECT NEWID() as id, FirstName, LastName, IdentityNum, CommAddress FROM PROCSIN_V3.dbo.cdCurrAcc\n" +
            "LEFT JOIN PROCSIN_V3.dbo.prCurrAccCommunication ON cdCurrAcc.CurrAccCode = prCurrAccCommunication.CurrAccCode AND CommunicationTypeCode = 3\n" +
            "WHERE cdCurrAcc.CurrAccCode = :AccCode";

    public static String GET_NON_PRINTED_ORDERS = "SELECT TOP (20) NEWID() as id, orderCode, deliveryName as fullName, NULL as invoiceURL FROM PRS_SEVK.sevk.Orders o, PRS_SEVK.sevk.OrderLog l\n" +
            "WHERE isPrinted = 0 AND o.id = l.order_id AND l.status = 1";

}
