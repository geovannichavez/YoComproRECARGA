package com.globalpaysolutions.yocomprorecarga.api;

import com.globalpaysolutions.yocomprorecarga.models.Countries;
import com.globalpaysolutions.yocomprorecarga.models.OperatorsResponse;
import com.globalpaysolutions.yocomprorecarga.models.SimpleMessageResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.AgesResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.AuthenticaReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.AuthenticateResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.LeaderboardReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.LeaderboardsResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.NicknameReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.PrizesHistoryResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.RegisterClientResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.RegisterPhoneConsumerReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.RequestTopupReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.SimpleResultResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.SouvenirsResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.StoreAirtimeReportReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.TokenReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.TokenValidationBody;
import com.globalpaysolutions.yocomprorecarga.models.api.Tracking;
import com.globalpaysolutions.yocomprorecarga.models.api.WinPrizeResponse;
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
    Call<SimpleMessageResponse> requestTokenValidation(@Header("authenticationKey") String pAuthKey, @Body TokenValidationBody pTokenValBody);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.REQUESTTOPUP)
    Call<SimpleMessageResponse> requestTopup(@Header("authenticationKey") String pAuthKey, @Body RequestTopupReqBody pRequestTopupBody);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.REGISTER_PHONE_CONSUMER)
    Call<RegisterClientResponse> registerConsumer(@Header("authenticationKey") String pAuthKey, @Body RegisterPhoneConsumerReqBody pRegisterConsumerBody);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.SEND_STORE_AIRTIME_REPORT)
    Call<SimpleMessageResponse> sendStoreAirtimeReport(@Header("authenticationKey") String pAuthKey, @Body StoreAirtimeReportReqBody pStoreAirtimeReportReqBody);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.EXCHANGE)
    Call<ExchangeResponse> exchangeChest(@Header("authenticationKey") String pAuthKey, @Body ExchangeReqBody pExchangeCoin);

    @Headers("Content-Type: application/json")
    @GET(StringsURL.TRACKING)
    Call<Tracking> getConsumerTracking(@Header("authenticationKey") String pAuthKey);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.AUTHENTICATE_CONSUMER)
    Call<AuthenticateResponse> authenticateConsumer(@Body AuthenticaReqBody pAuthenticateBody);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.VALIDATE_NICKNAME)
    Call<SimpleResultResponse> registerNickname(@Header("authenticationKey") String pAuthKey, @Body NicknameReqBody pNicknameRequest);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.REDEEM_PRIZE)
    Call<WinPrizeResponse> redeemPrize(@Header("authenticationKey") String pAuthKey);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.LEADERBOARDS)
    Call<LeaderboardsResponse> retrieveLeaderboards(@Header("authenticationKey") String pAuthKey, @Body LeaderboardReqBody pInterval);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.PRIZES_HISTORY)
    Call<PrizesHistoryResponse> retrievePrizsHistory(@Header("authenticationKey") String pAuthKey);

    @Headers("Content-Type: application/json")
    @GET(StringsURL.GET_ERAS)
    Call<AgesResponse> retrieveAges(@Header("authenticationKey") String pAuthKey);

    @Headers("Content-Type: application/json")
    @GET(StringsURL.SOUVENIRS)
    Call<SouvenirsResponse> getSouvenirs(@Header("authenticationKey") String pAuthKey);

}
