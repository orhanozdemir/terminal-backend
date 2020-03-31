package com.procsin.TrendyolAPI.Model;

import java.util.List;

public class TrendyolOrdersResponseModel {
    public int page;
    public int size;
    public int totalPages;
    public int totalElements;
    public List<TrendyolOrder> content;
}
