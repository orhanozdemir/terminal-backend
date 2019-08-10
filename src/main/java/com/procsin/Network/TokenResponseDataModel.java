package com.procsin.Network;

import java.util.List;

public class TokenResponseDataModel {
    private String success;
    private List<TokenResponseModel> data;

    public TokenResponseDataModel() {}

    public TokenResponseDataModel(String success, List<TokenResponseModel> data) {
        this.success = success;
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<TokenResponseModel> getData() {
        return data;
    }

    public void setData(List<TokenResponseModel> data) {
        this.data = data;
    }
}
