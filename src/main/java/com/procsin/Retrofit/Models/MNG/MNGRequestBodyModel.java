package com.procsin.Retrofit.Models.MNG;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;

@NamespaceList({
        @Namespace(reference = "http://tempuri.org/")
})
public class MNGRequestBodyModel {
    @Element(name = "MNGGonderiBarkod")
    public MNGGonderiBarkod MNGGonderiBarkod;
}
