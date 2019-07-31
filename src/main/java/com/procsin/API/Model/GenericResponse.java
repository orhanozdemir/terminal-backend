package com.procsin.API.Model;

public class GenericResponse {

    public Boolean success;
    public String message;

    public GenericResponse() {}

    public GenericResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

}
