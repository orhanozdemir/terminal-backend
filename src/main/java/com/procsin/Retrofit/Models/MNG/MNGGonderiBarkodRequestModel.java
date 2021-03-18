package com.procsin.Retrofit.Models.MNG;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;

public class MNGGonderiBarkodRequestModel {
    @Element(name = "MNGGonderiBarkod")
    public MNGRequestBodyModel requestBodyModel;
}
