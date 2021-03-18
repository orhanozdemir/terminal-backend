package com.procsin.Retrofit.Models.MNG.Response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;

@NamespaceList({
        @Namespace(reference = "http://tempuri.org/")
})
public class MNGResponseBodyModel {
    @Element(name = "MNGGonderiBarkodResponse")
    public MNGGonderiBarkodResponse responseBody;
}
