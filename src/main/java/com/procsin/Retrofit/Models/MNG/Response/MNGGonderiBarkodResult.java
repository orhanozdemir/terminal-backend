package com.procsin.Retrofit.Models.MNG.Response;

import org.simpleframework.xml.Element;

public class MNGGonderiBarkodResult {
    @Element(name = "ReferansNo")
    public String ReferansNo;
    @Element(name = "IstekBasarili")
    public int IstekBasarili;
    @Element(name = "IstekHata", required = false)
    public String IstekHata;
    @Element(name = "MngKargoFaturaSeriNo", required = false)
    public String MngKargoFaturaSeriNo;
    @Element(name = "MngKargoGonderiNo", required = false)
    public String MngKargoGonderiNo;
    @Element(name = "GonderiBarkods", required = false)
    public MNGGonderiBarkods GonderiBarkods;

}
