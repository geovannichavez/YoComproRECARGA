package com.globalpaysolutions.yocomprorecarga.api;

import com.globalpaysolutions.yocomprorecarga.models.Countries;
import com.globalpaysolutions.yocomprorecarga.models.OperatorsResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.RegisterClientResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.RegisterConsumerReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.RequestTopupReqBody;
import com.globalpaysolutions.yocomprorecarga.models.SimpleMessageResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.TokenReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.TokenValidationBody;
import com.globalpaysolutions.yocomprorecarga.utils.StringsURL;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Josué Chávez on 13/01/2017.
 */

public interface ApiInterface
{
    @GET(StringsURL.COUNTRIES)
    Call<Countries> getCountries();

    @GET(StringsURL.OPERATORS)
    Call<OperatorsResponse> getOperators(@Header("Country-Type") int countryID);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.TOKEN)
    Call<SimpleMessageResponse> requestPhoneValidationResult(@Body TokenReqBody pTokenReqBody);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.VALIDATE_TOKEN)
    Call<SimpleMessageResponse> requestTokenValidation(@Body TokenValidationBody pTokenValBody);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.REQUESTTOPUP)
    Call<SimpleMessageResponse> requestTopup(@Body RequestTopupReqBody pRequestTopupBody);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.REGISTER_CONSUMER)
    Call<RegisterClientResponse> registerConsumer(@Body RegisterConsumerReqBody pRegisterConsumerBody);


}
