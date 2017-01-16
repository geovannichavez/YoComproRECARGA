package com.globalpaysolutions.yocomprorecarga.api;

import com.globalpaysolutions.yocomprorecarga.models.Countries;
import com.globalpaysolutions.yocomprorecarga.models.OperatorsResponse;
import com.globalpaysolutions.yocomprorecarga.utils.StringsURL;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by Josué Chávez on 13/01/2017.
 */

public interface ApiInterface
{
    @GET(StringsURL.COUNTRIES)
    Call<Countries> getCountries();

    @GET(StringsURL.OPERATORS)
    Call<OperatorsResponse> getOperators(@Header("Country-Type") int countryID);
}
