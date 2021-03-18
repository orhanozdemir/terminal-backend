package com.procsin.Retrofit.Models.MNG;

import org.simpleframework.xml.Element;

public class MNGReqModel {
    @Element(name = "WsUserName")
    public String WsUserName;
    @Element(name = "WsPassword")
    public String WsPassword;
    @Element(name = "ReferansNo")
    public String ReferansNo;
    @Element(name = "OutBarkodType")
    public String OutBarkodType;
    /*@Element(name = "UrunBedeli")
    public double UrunBedeli;
    @Element(name = "FlKapidaTahsilat")
    public int FlKapidaTahsilat;*/
    @Element(name = "HatadaReferansBarkoduBas")
    public int HatadaReferansBarkoduBas;
//    @Element(name = "ParcaBilgi")
//    public MNGGonderiParca ParcaBilgi;
}