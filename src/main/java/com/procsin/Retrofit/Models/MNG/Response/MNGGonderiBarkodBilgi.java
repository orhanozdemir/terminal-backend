package com.procsin.Retrofit.Models.MNG.Response;

import org.simpleframework.xml.Element;

public class MNGGonderiBarkodBilgi {
    @Element(name = "BarkodParcaNo", required = false)
    public int BarkodParcaNo;
    @Element(name = "BarkodValue", required = false)
    public String BarkodValue;
    @Element(name = "BarkodText", required = false)
    public String BarkodText;
}
