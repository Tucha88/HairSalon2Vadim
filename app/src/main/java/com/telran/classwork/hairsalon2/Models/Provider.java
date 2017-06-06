package com.telran.classwork.hairsalon2.Models;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by vadim on 15.03.2017.
 */

public class Provider {
//    public static final String BASE_URL = "https://hear-saloon.herokuapp.com/rest/masterservice";
    public static final String BASE_URL = "https://hair-salon-personal.herokuapp.com";
    private static Provider instance = null;

    public Provider() {
    }
    public static Provider getInstance(){
        if (instance == null){
            instance = new Provider();
        }
        return instance;
    }
    public Response put(String path, String data, String token)throws IOException {
        OkHttpClient client = new OkHttpClient();
//        client.setConnectTimeout(15, TimeUnit.SECONDS);
//        client.setReadTimeout(15,TimeUnit.SECONDS);
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(type,data);
        Request request = new Request.Builder()
                .url(BASE_URL+path)
                .put(requestBody)
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }
    public Response post(String path, String data, String token)throws IOException {
        OkHttpClient client = new OkHttpClient();
//        client.setConnectTimeout(15, TimeUnit.SECONDS);
//        client.setReadTimeout(15,TimeUnit.SECONDS);
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(type,data);
        Request request = new Request.Builder()
                .url(BASE_URL+path)
                .post(requestBody)
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }
    public Response get(String path, String token)throws IOException {
        OkHttpClient client = new OkHttpClient();
//        client.setConnectTimeout(15, TimeUnit.SECONDS);
//        client.setReadTimeout(15,TimeUnit.SECONDS);

        Request request = new Request.Builder()
                .url(BASE_URL+path)
                .get()
                .addHeader("Authorization",token)
                .addHeader("Content-type","application/json; charset=utf-8")
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

}

