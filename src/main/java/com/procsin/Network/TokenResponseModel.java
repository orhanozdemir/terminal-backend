package com.procsin.Network;

public class TokenResponseModel {

    private String username;
    private String token;
    private String expirationTime;

    public TokenResponseModel() {}

    public TokenResponseModel(String username, String token, String expirationTime) {
        this.username = username;
        this.token = token;
        this.expirationTime = expirationTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }
}
