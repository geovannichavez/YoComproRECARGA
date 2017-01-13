package com.globalpaysolutions.yocomprorecarga.api;

import com.globalpaysolutions.yocomprorecarga.models.Countries;
import com.globalpaysolutions.yocomprorecarga.utils.StringsURL;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Josué Chávez on 13/01/2017.
 */

public interface ApiInterface
{
    @GET(StringsURL.COUNTRIES)
    Call<Countries> getCountries();
}
