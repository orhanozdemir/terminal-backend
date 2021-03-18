package com.procsin.API.Service.Implementation.Pack;

import com.procsin.API.Model.ResponseModel.MNGBarcodeResponseModel;
import com.procsin.API.Service.Interface.Pack.MNGService;
import com.procsin.Retrofit.Interfaces.MNGInterface;
import com.procsin.Retrofit.Models.MNG.*;
import com.procsin.Retrofit.Models.MNG.Response.ResponseEnvelope;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import java.io.IOException;

@Service
public class MNGServiceImpl implements MNGService {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://service.mngkargo.com.tr/")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();

    private static Strategy strategy = new AnnotationStrategy();
    private static Serializer serializer = new Persister(strategy);

    MNGInterface mngInterface = retrofit.create(MNGInterface.class);

    public MNGBarcodeResponseModel getMNGBarcode(String orderCode) {
        RequestEnvelope requestEnvelope = new RequestEnvelope();
        MNGRequestBodyModel bodyModel = new MNGRequestBodyModel();
        MNGGonderiBarkod gonderiBarkod = new MNGGonderiBarkod();

        MNGReqModel reqModel = new MNGReqModel();
        reqModel.WsUserName = "419059978";
        reqModel.WsPassword = "UHMUCFEU";
        reqModel.ReferansNo = orderCode;
        reqModel.OutBarkodType = "ZPL";
        reqModel.HatadaReferansBarkoduBas = 1;

        gonderiBarkod.mngReqModel = reqModel;
        bodyModel.MNGGonderiBarkod = gonderiBarkod;
        requestEnvelope.requestBody = bodyModel;

        try {
            ResponseEnvelope response = mngInterface.getGonderiBarkod("MNGGonderiBarkodV3", requestEnvelope).execute().body();

            if (response != null && response.bodyModel != null && response.bodyModel.responseBody != null && response.bodyModel.responseBody.MNGGonderiBarkodResult != null) {
                System.out.println(response.bodyModel.responseBody.MNGGonderiBarkodResult.IstekBasarili);
                System.out.println(response.bodyModel.responseBody.MNGGonderiBarkodResult.IstekHata);
                System.out.println(response.bodyModel.responseBody.MNGGonderiBarkodResult.ReferansNo);
                System.out.println(response.bodyModel.responseBody.MNGGonderiBarkodResult.MngKargoGonderiNo);
                System.out.println(response.bodyModel.responseBody.MNGGonderiBarkodResult.MngKargoFaturaSeriNo);
                System.out.println(response.bodyModel.responseBody.MNGGonderiBarkodResult.GonderiBarkods.GonderiBarkodBilgi.BarkodText);
                if (response.bodyModel.responseBody.MNGGonderiBarkodResult.GonderiBarkods != null && response.bodyModel.responseBody.MNGGonderiBarkodResult.GonderiBarkods.GonderiBarkodBilgi != null) {
                    if (response.bodyModel.responseBody.MNGGonderiBarkodResult.GonderiBarkods.GonderiBarkodBilgi.BarkodText != null) {
                        MNGBarcodeResponseModel responseModel = new MNGBarcodeResponseModel();
                        responseModel.barcodeText = response.bodyModel.responseBody.MNGGonderiBarkodResult.GonderiBarkods.GonderiBarkodBilgi.BarkodText;
                        return responseModel;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
