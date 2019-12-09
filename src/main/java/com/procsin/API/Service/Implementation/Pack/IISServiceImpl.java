package com.procsin.API.Service.Implementation.Pack;

import com.procsin.API.Service.Interface.Pack.IISService;
import com.procsin.Retrofit.Interfaces.IISInterface;
import com.procsin.Retrofit.Models.AuthResponseModel;
import com.procsin.Retrofit.Models.InvoiceRequestModel;
import com.procsin.Retrofit.Models.InvoiceResponseModel;
import com.procsin.Retrofit.Models.OrderDetailsModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public InvoiceResponseModel createInvoice(String orderCode) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.17.5.196:1515")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IISInterface repo = retrofit.create(IISInterface.class);

        updateWarehouseCode(orderCode);
        List<OrderDetailsModel> orderDetails = getOrderDetails(orderCode);

        boolean isTrendyol = orderCode.contains("TY");
        InvoiceRequestModel requestModel = createRequestModel(orderDetails, isTrendyol);
        try {
            Response<AuthResponseModel> authResponse = repo.auth().execute();
            Response<InvoiceResponseModel> invoiceResponse = repo.invoice("(S(" + authResponse.body().SessionID + "))",requestModel).execute();
            return invoiceResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    Integer updateWarehouseCode(String orderCode) {
        String updateWarehouseQuery = "UPDATE PROCSIN_V3.dbo.trOrderHeader SET WarehouseCode = '1-2-1' WHERE InternalDescription = :InternalDescription";
        em.flush();
        Integer result = em.createNativeQuery(updateWarehouseQuery).setParameter("InternalDescription",orderCode).executeUpdate();
        em.clear();
        return result;
    }

//    @Transactional
//    Integer checkValidation(String accCode) {
//        List<RetailCustomerModel> customerModels = em.createNativeQuery(Queries.GET_RETAIL_CUSTOMER,RetailCustomerModel.class)
//                .setParameter("AccCode",accCode).getResultList();
//        RetailCustomerModel model = customerModels.get(0);
//
//        if(isNameValid(model.FirstName)) {
//
//        }
//        if(isNameValid(model.LastName)) {
//
//        }
//        if(isEmailValid(model.CommAddress)) {
//
//        }
//        if(isIdentityValid(model.IdentityNum)) {
//            model.IdentityNum = "11111111111";
//        }
//
//    }

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
