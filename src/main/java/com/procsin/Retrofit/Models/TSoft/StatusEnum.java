package com.procsin.Retrofit.Models.TSoft;

public enum StatusEnum {

    URUN_HAZIRLANIYOR("1204"),
    URUN_PAKETLENIYOR("1202"),
    TEDARIK_SURECINDE("1203"),
    BEKLEMEDE("1209"),
    KARGOYA_VERILECEK("1217"),
    KARGO_HAZIR("5"),
    KARGOYA_VERILDI("6"),
    TESLIM_EDILDI("8"),
    IPTAL_EDILDI("9"),
    IADE_EDILDI("10"),
    KISMI_IADE_EDILDI("11"),
    CIKIS_BEKLENIYOR("1206"),
    YENIDEN_CIKIS_TEDARIK("1208"),
    MUSTERIYE_ULASILAMADI("1205");


    public final String statusId;

    private StatusEnum(String statusId) {
        this.statusId = statusId;
    }
}