package com.procsin.Retrofit.Models.MNG;

import org.simpleframework.xml.Element;

/**
 * Description: Parameters
 * Created by PHJ on 2019/5/5.
 */
public class RequestModel {

    @Element(name = "theCityName")
    public String cityName; // city name


    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}