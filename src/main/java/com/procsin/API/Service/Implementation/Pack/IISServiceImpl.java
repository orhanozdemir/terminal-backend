package com.procsin.API.Service.Implementation.Pack;

import com.procsin.API.Service.Interface.Pack.IISService;
import com.procsin.Retrofit.Interfaces.IISInterface;
import com.procsin.Retrofit.Models.AuthResponseModel;
import com.procsin.Retrofit.Models.InvoiceRequestModel;
import com.procsin.Retrofit.Models.InvoiceResponseModel;
import com.procsin.Retrofit.Models.OrderDetailsModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class IISServiceImpl implements IISService {

    @PersistenceContext
    EntityManager em;

    @Override
    public void createInvoice(String orderCode) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://176.236.208.115:1515")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IISInterface repo = retrofit.create(IISInterface.class);

        updateWarehouseCode(orderCode);
        List<OrderDetailsModel> orderDetails = getOrderDetails(orderCode);

        boolean isTrendyol = orderCode.contains("TY");
        InvoiceRequestModel requestModel = createRequestModel(orderDetails, isTrendyol);
        repo.auth().enqueue(new Callback<AuthResponseModel>() {
            @Override
            public void onResponse(Call<AuthResponseModel> call, Response<AuthResponseModel> response) {
                repo.invoice("(S(" + response.body().SessionID + "))",requestModel).enqueue(new Callback<InvoiceResponseModel>() {
                    @Override
                    public void onResponse(Call<InvoiceResponseModel> call, Response<InvoiceResponseModel> response) {

                    }

                    @Override
                    public void onFailure(Call<InvoiceResponseModel> call, Throwable throwable) {

                    }
                });
            }

            @Override
            public void onFailure(Call<AuthResponseModel> call, Throwable throwable) {

            }
        });
    }

    @Transactional
    Integer updateWarehouseCode(String orderCode) {
        String updateWarehouseQuery = "UPDATE PROCSIN_V3.dbo.trOrderHeader SET WarehouseCode = '1-2-1' WHERE InternalDescription = :InternalDescription";
        em.flush();
        Integer result = em.createNativeQuery(updateWarehouseQuery).setParameter("InternalDescription",orderCode).executeUpdate();
        em.clear();
        return result;
    }

    boolean isNameValid(String name) {
        return true;
    }

    boolean isEmailValid(String email) {
        return true;
    }

    boolean isIdentityValid(String identity) {
        return identity.length() == 11;
    }

    List<OrderDetailsModel> getOrderDetails(String orderCode) {
        StoredProcedureQuery query = em.createStoredProcedureQuery("PROCSIN_V3.dbo.usp_Getsiparis",OrderDetailsModel.class);
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.setParameter(1,orderCode).execute();
        return query.getResultList();
    }

    InvoiceRequestModel createRequestModel(List<OrderDetailsModel> models, boolean isTrendyol) {
        return new InvoiceRequestModel(models, isTrendyol);
    }

}
