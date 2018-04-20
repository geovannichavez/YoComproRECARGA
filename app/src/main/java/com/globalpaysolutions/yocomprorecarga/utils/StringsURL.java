package com.globalpaysolutions.yocomprorecarga.utils;

/**
 * Created by Josué Chávez on 13/01/2017.
 */

public class StringsURL
{
    //DESARROLLO
    //public final static String URL_BASE = "http://csncusgats.cloudapp.net:8074/v1/";

    //PRE-PRODUCCION
    //public final static String URL_BASE = "http://csncusgats.cloudapp.net:8074/PreTem/";

    //API V2 - PRE PRODUCCION
    //public final static String URL_BASE = "http://csncusgats.cloudapp.net:8073/v1/";

    //API v2
    //public final static String URL_BASE = "http://csncusgats1.northcentralus.cloudapp.azure.com:8073/v1/";

    //TODO: Cambiar antes de publciar
    //PRODUCCION
    public final static String URL_BASE  = "https://api.yovendorecarga.com/RG/";

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

    public final static String REDEEM_PRIZE = "WinPrize";

    public static final String POS_YOCOMPRORECARGA = "http://csncusgats.cloudapp.net:8074/v1/view/CreditCard/Index";

    public static final String LEADERBOARDS = "leaderboards/";

    public static final String PRIZES_HISTORY = "prize/history";

    public static final String GET_ERAS = "GetAges";

    public static final String SOUVENIRS = "SouvenirsByConsumer";

    public static final String STORE_ITEMS = "StoreItems";

    public static final String GET_COMBOS = "GetCombos";

    public static final String EXCHANGE_COMBOS = "ExchangeCombo";

    //FIREBASE
    private static final String URL_FIREBASE = "https://yocomprorecarga-development.firebaseio.com/";

    public static final String STATIC_POINTS = URL_FIREBASE + "staticPoints";
    public static final String DATA_STATIC_PONTS = URL_FIREBASE + "dataStaticPoints";

    //OTRAS URL'S
    public final static String YVR_STORE = "https://play.google.com/store/apps/details?id=com.globalpaysolutions.yovendorecarga";
    public final static String TUTORIAL_VIDEO_URL = "http://www.recar-go.com/home/tutorial/";
    public final static String TERMS_AND_CONDITIONS_URL = "http://www.recar-go.com/home/terminos/";

    //WIKITUDE
    private static final String ARCHITECT_SDK = "architectsdk://";

    public static final String ARCH_GOLD = ARCHITECT_SDK + "Gold";

    public static final String ARCH_SILVER = ARCHITECT_SDK + "Silver";

    public static final String ARCH_BRONZE = ARCHITECT_SDK + "Bronze";

    public static final String PURCHASE = "Purchase";
    public static final String ERA_SELECTION = "SelectAge";
    public static final String ACHIEVEMENTS = "GetAchievementsByConsumer";
    public static final String EXCHANGE_SOUVENIR = "ExchangeSouvenir";
    public static final String EXCHANGE_WILDCARD = "ExchangeWildcard";
    public static final String ACTIVATE_PRIZE = "ActivatePrize";
    public static final String CHALLENGES = "Challenge/Get";
    public static final String CREATE_CHALLENGE = "Challenge/Create";
    public static final String UPDATE_CHALLENGE = "Challenge/Update";
    public static final String GET_PENDING_CHALLENGES = "Challenge/GetPendingChallenges";
    public static final String REQUEST_REWARD_LIKES = "ActionSocialNetworks";
    public static final String GET_TRIVIA = "GetTrivia";
    public static final String RESPOND_TRIVIA = "TriviaResponse";
    public static final String GET_SOUVS_PROGRESS = "GetSouvenirProgress";
    public static final String GET_WORLDCUP_COUNTRIES = "GetCountries";
    public static final String SELECT_WORLDCUP_COUNTRY = "SelectCountries";
}

