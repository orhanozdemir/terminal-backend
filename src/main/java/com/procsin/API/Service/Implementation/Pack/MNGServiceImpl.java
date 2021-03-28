package com.procsin.API.Service.Implementation.Pack;

import com.procsin.API.Model.ResponseModel.MNGBarcodeResponseModel;
import com.procsin.API.Service.Interface.Pack.MNGService;
import com.procsin.Configuration.BasicAuthInterceptor;
import com.procsin.Retrofit.Interfaces.MNGInterface;
import com.procsin.Retrofit.Models.MNG.*;
import com.procsin.Retrofit.Models.MNG.Response.ResponseEnvelope;
import okhttp3.OkHttpClient;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class MNGServiceImpl implements MNGService {

    private final OkHttpClient httpClient = new OkHttpClient.Builder()
            .callTimeout(300, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .writeTimeout(300, TimeUnit.SECONDS)
            .connectTimeout(300, TimeUnit.SECONDS)
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://service.mngkargo.com.tr/")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .client(httpClient)
            .build();

    private static Strategy strategy = new AnnotationStrategy();
    private static Serializer serializer = new Persister(strategy);

    MNGInterface mngInterface = retrofit.create(MNGInterface.class);

    public MNGBarcodeResponseModel getMNGBarcode(String orderCode) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
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
            System.out.println("-----------------------------------------------------");
            System.out.println(orderCode);
            System.out.println(dateFormat.format(new Date()) + " - before barcode");
            ResponseEnvelope response = mngInterface.getGonderiBarkod("MNGGonderiBarkodV3", requestEnvelope).execute().body();
            System.out.println(dateFormat.format(new Date()) + " - after barcode");
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
