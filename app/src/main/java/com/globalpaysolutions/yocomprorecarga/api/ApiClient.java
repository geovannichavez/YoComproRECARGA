package com.globalpaysolutions.yocomprorecarga.api;

import com.globalpaysolutions.yocomprorecarga.utils.StringsURL;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Josué Chávez on 13/01/2017.
 */

public class ApiClient
{
    private static Retrofit retrofit = null;

    public static Retrofit getClient()
    {
        if (retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(StringsURL.URL_BASE)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(40, TimeUnit.SECONDS)
            .build();
}
