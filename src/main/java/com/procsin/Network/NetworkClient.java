package com.procsin.Network;

import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;

public class NetworkClient {

    private static final NetworkClient networkClient = new NetworkClient();

    private String baseAPIUrl = "https://www.procsin.com/rest1";
    private HttpHeaders headers = new HttpHeaders();

    public String APIToken = "-";
    private RestTemplate restTemplate = new RestTemplate();

    private NetworkClient() {
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
        restTemplate.getMessageConverters().add(0, converter);
        requestAPIToken();
    }

    public static NetworkClient getInstance() {
        return networkClient;
    }

    public <T> T doGET(String path, Class<T> tClass) {
        return doGET(path,null, tClass);
    }

    public <T> T doGET(String path, HashMap<String,String> parameters, Class<T> tClass) {
        String url = baseAPIUrl + path + "?api_token=" + APIToken;

        if (parameters != null) {
            for(String key : parameters.keySet()) {
                url += "&" + key + "=" + parameters.get(key);
            }
        }

        T response = restTemplate.getForObject(
                url,
                tClass);
        return response;
    }

    public <T> T doPost(String path, Class<T> tClass) {
        return doPost(path,null, tClass);
    }

    public <T> T doPost(String path, MultiValueMap<String, String> params, Class<T> tClass) {
        String url = baseAPIUrl + path;

        if (params == null) {
            params = new LinkedMultiValueMap<String, String>();
        }
        params.add("token", APIToken);

        HttpEntity<MultiValueMap<String, String>> entity =
                new HttpEntity<MultiValueMap<String, String>>(params, headers);

        ResponseEntity<T> response =
                restTemplate.exchange(url,
                        HttpMethod.POST,
                        entity,
                        tClass);

        return response.getBody();
    }

    public void requestAPIToken() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("pass","prs300377");
        TokenResponseModel tokenResponseModel = doPost("/auth/login/Mobile",params,TokenResponseDataModel.class).getData().get(0);
        APIToken = tokenResponseModel.getToken();
    }

}