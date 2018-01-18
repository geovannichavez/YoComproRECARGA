package com.globalpaysolutions.yocomprorecarga.api;

import com.globalpaysolutions.yocomprorecarga.models.Countries;
import com.globalpaysolutions.yocomprorecarga.models.OperatorsResponse;
import com.globalpaysolutions.yocomprorecarga.models.SimpleMessageResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.AchievementsResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ActivatePrizeReq;
import com.globalpaysolutions.yocomprorecarga.models.api.ActivatePrizeResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.AgesResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.AuthenticaReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.AuthenticateResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.CombosResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.EraSelectionReq;
import com.globalpaysolutions.yocomprorecarga.models.api.EraSelectionResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeComboReq;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeSouvenirReq;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeWildcardReq;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeWildcardResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.LeaderboardReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.LeaderboardsResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.NicknameReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.PrizesHistoryResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.PurchaseItemResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.PurchaseStoreReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.RegisterClientResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.RegisterPhoneConsumerReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.RequestTopupReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.SimpleResultResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.SouvenirsResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.StoreAirtimeReportReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.StoreItemsResponse;
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
    Call<RegisterClientResponse> registerConsumer(@Header("authenticationKey") String pAuthKey, @Header("AppVersion") String pAppVersion,
                                                  @Header("Platform") String pPlatform,
                                                  @Body RegisterPhoneConsumerReqBody pRegisterConsumerBody);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.SEND_STORE_AIRTIME_REPORT)
    Call<SimpleMessageResponse> sendStoreAirtimeReport(@Header("authenticationKey") String pAuthKey, @Body StoreAirtimeReportReqBody pStoreAirtimeReportReqBody);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.EXCHANGE)
    Call<ExchangeResponse> exchangeChest(@Header("authenticationKey") String pAuthKey, @Body ExchangeReqBody pExchangeCoin,
                                         @Header("AppVersion") String pAppVersion,
                                         @Header("Platform") String pPlatform);

    @Headers("Content-Type: application/json")
    @GET(StringsURL.TRACKING)
    Call<Tracking> getConsumerTracking(@Header("authenticationKey") String pAuthKey,
                                       @Header("AppVersion") String pAppVersion,
                                       @Header("Platform") String pPlatform);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.AUTHENTICATE_CONSUMER)
    Call<AuthenticateResponse> authenticateConsumer(@Body AuthenticaReqBody pAuthenticateBody, @Header("AppVersion") String pAppVersion,
                                                    @Header("Platform") String pPlatform);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.VALIDATE_NICKNAME)
    Call<SimpleResultResponse> registerNickname(@Header("authenticationKey") String pAuthKey, @Body NicknameReqBody pNicknameRequest);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.REDEEM_PRIZE)
    Call<WinPrizeResponse> redeemPrize(@Header("authenticationKey") String pAuthKey,
                                       @Header("AppVersion") String pAppVersion,
                                       @Header("Platform") String pPlatform);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.LEADERBOARDS)
    Call<LeaderboardsResponse> retrieveLeaderboards(@Header("authenticationKey") String pAuthKey, @Body LeaderboardReqBody pInterval);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.PRIZES_HISTORY)
    Call<PrizesHistoryResponse> retrievePrizsHistory(@Header("authenticationKey") String pAuthKey,
                                                     @Header("AppVersion") String pAppVersion,
                                                     @Header("Platform") String pPlatform);

    @Headers("Content-Type: application/json")
    @GET(StringsURL.GET_ERAS)
    Call<AgesResponse> retrieveAges(@Header("authenticationKey") String pAuthKey,
                                    @Header("AppVersion") String pAppVersion,
                                    @Header("Platform") String pPlatform);

    @Headers("Content-Type: application/json")
    @GET(StringsURL.SOUVENIRS)
    Call<SouvenirsResponse> getSouvenirs(@Header("authenticationKey") String pAuthKey,
                                         @Header("AppVersion") String pAppVersion,
                                         @Header("Platform") String pPlatform);

    @Headers("Content-Type: application/json")
    @GET(StringsURL.STORE_ITEMS)
    Call<StoreItemsResponse> getStoreItems(@Header("authenticationKey") String pAuthKey,
                                           @Header("AppVersion") String pAppVersion,
                                           @Header("Platform") String pPlatform);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.PURCHASE)
    Call<PurchaseItemResponse> purchaseStoreItem(@Header("authenticationKey") String pAuthKey,
                                                 @Header("AppVersion") String pAppVersion,
                                                 @Header("Platform") String pPlatform,
                                                 @Body PurchaseStoreReqBody request);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.ERA_SELECTION)
    Call<EraSelectionResponse> selectEra(@Header("authenticationKey") String pAuthKey,
                                         @Header("AppVersion") String pAppVersion,
                                         @Header("Platform") String pPlatform,
                                         @Body EraSelectionReq request);

    @Headers("Content-Type: application/json")
    @GET(StringsURL.ACHIEVEMENTS)
    Call<AchievementsResponse> getAchievements(@Header("authenticationKey") String pAuthKey,
                                               @Header("AppVersion") String pAppVersion,
                                               @Header("Platform") String pPlatform);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.EXCHANGE_SOUVENIR)
    Call<WinPrizeResponse> exchangeSouvenir(@Header("authenticationKey") String authKey,
                                            @Header("AppVersion") String pAppVersion,
                                            @Header("Platform") String pPlatform,
                                            @Body ExchangeSouvenirReq exchangeSouvenirReq);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.EXCHANGE_WILDCARD)
    Call<ExchangeWildcardResponse> exchangeWildcard(@Header("authenticationKey") String authKey,
                                                    @Header("AppVersion") String pAppVersion,
                                                    @Header("Platform") String pPlatform,
                                                    @Body ExchangeWildcardReq exchangeWildcardReq);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.ACTIVATE_PRIZE)
    Call<ActivatePrizeResponse> activatePrize(@Header("authenticationKey") String authKey,
                                              @Header("AppVersion") String pAppVersion,
                                              @Header("Platform") String pPlatform,
                                              @Body ActivatePrizeReq activatePrizeReq);
    @Headers("Content-Type: application/json")
    @GET(StringsURL.GET_COMBOS)
    Call<CombosResponse> getCombos(@Header("authenticationKey") String userAuthenticationKey,
                                   @Header("AppVersion") String versionName,
                                   @Header("Platform") String platform);

    @Headers("Content-Type: application/json")
    @POST(StringsURL.EXCHANGE_COMBOS)
    Call<WinPrizeResponse> exchangeCombo(@Header("authenticationKey") String userAuthenticationKey,
                                         @Header("AppVersion") String versionName,
                                         @Header("Platform") String platform,
                                         @Body ExchangeComboReq exchangeCombo);
}
