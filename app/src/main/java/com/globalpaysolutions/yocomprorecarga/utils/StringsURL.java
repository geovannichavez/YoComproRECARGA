package com.globalpaysolutions.yocomprorecarga.utils;

/**
 * Created by Josué Chávez on 13/01/2017.
 */

public class StringsURL
{
    //PRE-PRODUCCIÓN
    public final static String URL_BASE = "http://csncusgats.cloudapp.net:8074/v1/";

    public final static String COUNTRIES = "countries";

    public final static String OPERATORS = "reqTopupOperators";

    public final static String TOKEN = "token";

    public final static String VALIDATE_TOKEN = "validateToken";

    public final static String REQUESTTOPUP = "requestTopup";

    public final static String REGISTER_PHONE_CONSUMER = "registerPhoneConsumer";

    public final static String SEND_STORE_AIRTIME_REPORT = "sendLackCreditReport";

    public final static String EXCHANGE = "Exchange";

    public final static String TRACKING = "Tracking";

    public final static String AUTHENTICATE_CONSUMER = "ConsumerSignin";

    public final static String VALIDATE_NICKNAME = "InsertNicknameConsumer";

    public static final String POS_YOCOMPRORECARGA = "http://csncusgats.cloudapp.net:8074/v1/view/CreditCard/Index";

    //FIREBASE
    private static final String URL_FIREBASE = "https://yocomprorecarga-development.firebaseio.com/";

    public static final String STATIC_POINTS = URL_FIREBASE + "staticPoints";
    public static final String DATA_STATIC_PONTS = URL_FIREBASE + "dataStaticPoints";

    //OTRAS URL'S
    public final static String YVR_STORE = "https://play.google.com/store/apps/details?id=com.globalpaysolutions.yovendorecarga";

    //WIKITUDE
    private static final String ARCHITECT_SDK = "architectsdk://";

    public static final String ARCH_GOLD = ARCHITECT_SDK + "Gold";

    public static final String ARCH_SILVER = ARCHITECT_SDK + "Silver";

    public static final String ARCH_BRONZE = ARCHITECT_SDK + "Bronze";

}

