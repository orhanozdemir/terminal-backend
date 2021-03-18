package com.procsin.Retrofit.Models.MNG;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

// Root stands for outer layer
@Root(name = "soap:Envelope")
@NamespaceList({
        @Namespace(reference = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi"),
        @Namespace(reference = "http://www.w3.org/2001/XMLSchema", prefix = "xsd"),
        @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/", prefix = "soap")
})
public class RequestEnvelope {
    @Element(name = "soap:Body")
    public MNGRequestBodyModel requestBody;
}

//<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://www.w3.org/2003/05/soap-envelope">
//<soap:Body xmlns="http://WebXml.com.cn/">
//<getWeatherbyCityName>
//<theCityName>Hefei</theCityName>
//</getWeatherbyCityName>
//</soap:Body>
//</soap:Envelope>