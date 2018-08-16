package com.globalpaysolutions.yocomprorecarga.utils;

/**
 * Created by Josué Chávez on 06/03/2017.
 */

public class Constants
{
    public static final String APP_NAME = "RecarGO!";
    public static final String APP_SIMPLE_NAME = "RecarGO";
    //Google Location Manager
    //public static final int TEN_METTERS_DISPLACEMENT = 10;
    public static final int FOUR_METTERS_DISPLACEMENT = 4;
    public static final int ONE_METTER_DISPLACEMENT = 1;

    //Firebase
    public static final double SALES_POINTS_RADIUS_KM = 2;
    //public static final double VENDOR_RADIUS_KM = 5;
    public static final double VENDOR_RADIUS_KM = 1;
    public static final double PRIZES_STOP_RADIUS_KM = 2;
    public static final double BRONZE_CHESTS_QUERY_RADIUS_KM = 0.350;
    public static final double SILVER_CHESTS_QUERY_RADIUS_KM = 0.5;
    public static final double GOLD_CHESTS_QUERY_RADIUS_KM = 0.75;

    public static final double PLAYER_RADIUS_KM = 1; // 1,000 meters

    //Map
    public static final int GOOGLE_MAPS_ZOOM_CAMERA = 19;


    //Permissions
    public static final int REQUEST_PERMISSION_CODE = 1;

    //Intent extras
    public static final String VENDOR_CODE_REQUEST_EXTRA = "vendor_code_request_extra";

    //Intent extras: Notificactions
    public static final String NOTIFICATION_TITLE_EXTRA = "notification_title_extra";
    public static final String NOTIFICATION_BODY_EXTRA = "notification_body_extgra";
    public static final String NOTIFICATION_TITLE_SWITCH_ERA = "¡CAMBIA DE ERA YA!";

    //OneSignal
    public static final String ONESIGNAL_USER_TAG_KEY = "userid";

    //Wikitude
    //public static final String WIKITUDE_LICENSE_KEY = "sDhQ4W0tbAycdZMFcwYR2dVTNNNYgX38e61aDP1dE1tHpD0Cs7F1C85DZYVLisPytso30pc40JVHVKHP8+Q5PLo1BQ5KEgS5rAgtTRS9yHklcJL/XQqqgPFKtHHdrwKzJNTv2xAnvDDaVQv+NIJ4g6BDM6vJH6zOG/hukmW+y0FTYWx0ZWRfXywK/kdaLX2JDcQrkdWGD3byR204FA8Lwn0ubcMp2137jADN+MZ6wqjryxkgIc9j+fHd4KeJK1lG9TftwUcBa7FkrZ+oV+3hmhgls8KJwcc1C1pk0P0nrOIFrRxdf+NyDFwDjvvV3mqChHW7HrfRBk/SWQuKYLHcBr/sHrEYJ+wyWJz5gEvcszhrAdNjfaG4A1093WhPTllp+est2+iCV7JqQYT3Kzjj3wBZQ+ax9Yhohnhy0ufK09c/fPV05bui7pZ9CQ7w5h6fPIwv+44zHyFrMfN6mz4xPDWY+G0lDXW/KH+MNuyii+fkbTYMSN4/3/BVd+wbcDi4PmtNSa7VNJ++oKamVf6u0Qnoj5WBY8IQVt9sH1AXzHg+mSTW09qC/n2wYmxOEub8+5wAiD7JnfH2MzBogQEVOgAOx5F3wbtcylKbqUmYr6mN2tU2wmutKauu+1wkWtEQ3JcUzMO2oI01KJumB7rLT1hiSh/VkzE8mMg5s+El9DM=";
    public static final String WIKITUDE_LICENSE_KEY = "SAX0HsmTOzZpdSvk58FmNHXXd0q+7L6sNhNSLmSIvfNaekRFhWEvk/cmJcuLZyGadQnS7PDerr+Hwt9IvV2I8o/eeYWP6cP8uhptpfHGRFDNtJBIbCg5CH6yuWLjaX1qZvPk/MGeQPT3/sHc+7n8hCtr5SSxN9XXeGgdOkFk+iNTYWx0ZWRfXxnRfdUk3sUaSDwbJOHacO6lfT/v+HR5zwARDj3kkzbP8WcwI1N5B1pWFGh+xxuWG7qArGGwIoVDipIw1mRJ6Kdm2rx7TQrSS9GVb3nXlmJS0f+AS5HFKyrCAcUya3Ka2ZU4rJAjr6N7rhGvsR5T9vKr2OEjcgLD4KBhM3XWlBk2JU2uZ0QMLvyekIm5pGSTZJnxsIFYjtRGlORQwPJEmiPK5umuVizS7QF/kDvQXkkvLGs4ZMXw5eZcIFsQ+IikY4dJgNOqKtR1I5mc1IqrjPCE8ez+vZlW+HkDiBRV7Dh2afm8GXxn1P4NqEOEholNKKRjsxp3GMbN/gAx1yHgyXhSUphMrLys4/EiPzXpF9zzg+d8GsotNDRFaMWz27hMe1noX/18VQSpR1MD4mgT3jemajKEREu+yNVRXcLK67OgkezZr1IVj2xdvAxtOdgLfw0tmeYiHMYrXbtwG+/A4CrGx1c5krGtXzm+jnT3Uq2DkYqwyyWFUm6RYGnip5oSwDzk/rnRoJTFgF2VGlbeKvZQ0CqoBlXY2eCjJIoKaRcIoDsuOWSd7gjCcuB8GM6I46nCN6yMBgzINtyTCbhwfWeucIPe2tUaS+vb+dF9GvH6RG24tXrW69o=";

    //Wikitude: AR-POI
    //public static final double AR_POI_RADIOS_KM = 0.015; //15 meters
    public static final double AR_POI_RADIOS_KM = 0.017; //17 meters
    //public static final double AR_POI_RADIOS_KM = 0.030; //30 meters
    //public static final double AR_POI_RADIOS_KM = 0.03; //30 meters
    //public static final double AR_POI_RADIOS_KM = 0.02; //20 meters
    //public static final double AR_POI_RADIOS_KM = 0.05; //30 meters
    //public static final double AR_POI_RADIOS_KM = 1; // 1 KM

    //RecarGO 2D
    //public static final double RECARSTOP_2D_RADIUS_KM = 0.006;
    //public static final double RECARSTOP_2D_RADIUS_KM = 0.035; //35 meters
    //public static final double RECARSTOP_2D_RADIUS_KM = 0.02; //20 meters
    public static final double RECARSTOP_2D_RADIUS_KM = 0.015; //15 meters
    public static final int ONRADIUS_VIBRATION_TIME_MILLISECONDS = 100;
    public static final int ONRADIUS_VIBRATION_SLEEP_MILLISECONDS = 500;
    public static final int OUT_RADIUS_VIBRATION_TIME_MILLISECONDS = 130;
    public static final int OUT_RADIUS_VIBRATION_SLEEP_MILLISECONDS = 1800;
    public static final int ON_EARNED_COIN_SUCCESSFULLY_VIBRATION_MILLISECONDS = 400;
    public static final int REQUIRED_TIME_TOUCH_MILLISECONDS = 1000;

    //Chest Types
    public static final int VALUE_CHEST_TYPE_GOLD = 3;
    public static final int VALUE_CHEST_TYPE_SILVER = 2;
    public static final int VALUE_CHEST_TYPE_BRONZE = 1;
    public static final int VALUE_CHEST_TYPE_WILDCARD = 4;
    public static final String NAME_CHEST_TYPE_GOLD = "Gold";
    public static final String NAME_CHEST_TYPE_SILVER = "Silver";
    public static final String NAME_CHEST_TYPE_BRONZE = "Bronze";
    public static final String NAME_CHEST_TYPE_WILDCARD = "Wildcard";

    public static final String WELCOME_CHEST_FIREBASE_KEY = "FreeChest";

    //URL Map Properties
    public static final String URI_MAP_VALUE_CHEST_TYPE = "chestType";
    public static final String URI_MAP_VALUE_FIREBASE_ID = "firebaseID";
    public static final String URI_MAP_VALUE_LATITUDE = "latitude";
    public static final String URI_MAP_VALUE_LONGITUDE = "longitude";

    //Prize Detail Bundle Keys
    public static final String BUNDLE_PRIZE_TITLE = "bundle_data_prize_title";
    public static final String BUNDLE_PRIZE_CODE = "bundle_data_prize_code";
    public static final String BUNDLE_PRIZE_DIAL = "bundle_data_prize_dial";
    public static final String BUNDLE_PRIZE_TYPE = "bundle_data_prize_type";
    public static final String BUNDLE_PRIZE_IMAGE = "bundle_prize_image";
    public static final String BUNDLE_PRIZE_DESCRIPTION = "bundle_data_prize_description";
    public static final String BUNDLE_PRE_SET_LAST_PRIZE_CODE = "bundle_pre_set_last_prize_code";
    public static final String BUNDLE_CHALLENGE_ID = "bundle_challenge_received_id";
    public static final String BUNDLE_CHALLENGE_BET = "bundle_challenge_received_bet";

    //General bundle keys
    public static final String BUNDLE_TOKEN_VALIDATION = "bundle_data_usr_phone_validation";
    public static final String BUNDLE_PHONE_RETYPE = "bundle_data_phone_retype";
    public static final String BUNDLE_PHONE_TYPED = "bundle_data_phone_typed";


    //Bundles from Era Selection
    public static final String BUNDLE_ERA_SELECTION_INTENT_DESTINY = "bundle_era_selection_intent_destiny";
    public static final String BUNDLE_DESTINY_MAP = "bundle_destiny_map";
    public static final String BUNDLE_DESTINY_STORE = "bundle_destiny_store";
    public static final String BUNDLE_ERA_RESELECTION_ACTION = "bundle_era_reselection_action" ;
    public static final String BUNDLE_DESTINY_CHALLENGES = "bundle_destiny_challenges";
    //public static final String BUNDLE_SECOND_ERA_UPDATE_ERA_SELECTED_VALUE = "bundle_second_era_update_era_selected_value";

    public static final String BUNDLE_COMBOS_BACK_STACK = "bundle_combos_back_stack";
    public static final String BUNDLE_STORE_BACK_STACK = "bundle_store_back_stack";

    //Bundles from Map
    public static final String BUNDLE_CHALLENGE_USER_ID = "bundle_challenge_user_id";
    //public static final String BUNDLE_CHALLENGE_RECEIVED = "bundle_challenge_received";
    public static final String BUNDLE_CHALLENGE_OPPONENT_NICKNAME = "bundle_challenge_opponent_nickname";

    //Leaderboards
    public static final String TODAY = "Today";
    public static final String WEEK = "Week";
    public static final String MONTH = "Month";
    public static final String OVER_ALL = "OverAll";
    public static final String TRIVIA = "Trivia";

    //Prizes
    public static final String SMS_NUMBER_PRIZE_EXCHANGE = "323";

    //Chest Types
    public static final int CHEST_COINS_TYPE = 1;
    public static final int CHEST_SOUVENIR_TYPE = 2;

    //Chest states
    public static final String CHEST_STATE_OPEN = "open";
    public static final String CHEST_STATE_CLOSED = "closed";

    //Chest exchange speed
    public static final int DISTANCE_ALLOWED_STAGE_1 = 60; // Meters
    public static final int DISTANCE_ALLOWED_STAGE_2 = 650; // Meters
    public static final int DISTANCE_ALLOWED_STAGE_3 = 1400; // Meters

    public static final float SPEED_WALK_MPS_CHEST_EXCHANGE = 2; // 2 mps is equivalent to 120 meters per minute: 7.2 Kms/h
    public static final float SPEED_BIKE_MPS_CHEST_EXCHANGE = 3; // 3 mps is equivalent to 180 meters per minute: 10.8 Kms/h
                                                                 // 4 mps is equivalent to 240 meters per minute: 14.40 Kms/h
    public static final float SPEED_CAR_MPS_CHEST_EXCHANGE = 6;  // 6 mps is equivalento to 21.60 kph
                                                                 // 7 mps is equivalento to 25.20 kph
                                                                 // 9 mps is equivalento to 32.40 kph
                                                                 // 16 mps is equivalento to 57.60 kph
                                                                 // 27 mps is equivalent to 97.2 kph
                                                                 // 33 mps is equivalent to 118.8 kph
                                                                 // 24 mps is equivalent to 86.4 kph
                                                                 // 6 mps equals to 21.6 kph (average bike riding)
    public static final float DISTANCE_BETWEEN_CHESTS_METERS = 30;



    public static final int SPEED_PENALTY_TIME_MILLIS = 120000;

    public static final String ACHIEVEMENT_FROM_SOUVENIR_SALE = "achievement_from_souvenir_sale";

    //Showcase View
    public static final Integer SHOWCASE_PAINT_TITLE = 0;
    public static final Integer SHOWCASE_PAINT_CONTENT = 1;
    public static final String INTENT_EXTRA_COUNTDOWN = "prize_countdown_extra";
    public static final String NOTIFICATION_ID_COUNTDOWN = "notification_id_countdown";
    public static final long PRIZE_EXCHANGE_TIME_REQUIRED = 1000 * 60 * 60 * 10;
    //public static final long PRIZE_EXCHANGE_TIME_REQUIRED = 1000 * 18;

    //GeoFire References data
    public static final String BUNDLE_GEOFIRE_GOLD_REFERENCE = "bundle_geofire_gold_reference" ;
    public static final String BUNDLE_GEOFIRE_SILVER_REFERENCE = "bundle_geofire_silver_reference";
    public static final String BUNDLE_GEOFIRE_BRONZE_REFERENCE = "bundle_geofire_bronze_reference";
    public static final String BUNDLE_GEOFIRE_WILDCARD_REFERENCE = "bundle_geofire_wildcard_reference";


    public static final String TAG_MARKER_SALEPOINT = "tag_marker_salepoint";
    public static final String TAG_MARKER_VENDOR = "tag_marker_vendor";
    public static final String TAG_MARKER_PLAYER = "tag_marker_player";
    public static final String TAG_MARKER_GOLD = "tag_marker_gold";
    public static final String TAG_MARKER_SILVER = "tag_marker_silver";
    public static final String TAG_MARKER_BRONZE = "tag_marker_bronze";
    public static final String TAG_MARKER_WILDCARD = "tag_marker_wildcard";


    //Challenge
    public static final int CHALLENGE_PAPER_VALUE = 1;
    public static final int CHALLENGE_ROCK_VALUE = 2;
    public static final int CHALLENGE_SCISSORS_VALUE = 3;
    public static final double CHALLENGE_BET_VALUE_1 = 10;
    public static final double CHALLENGE_BET_VALUE_2 = 25;
    public static final double CHALLENGE_BET_VALUE_3 = 50;
    public static final String BUNDLE_CHALLENGE_RESULT_SERIALIZABLE = "bundle_challenge_result_serializable";
    public static final String BUNDLE_CHALLENGE_USER_MOVE = "bundle_challenge_user_move";
    public static final String BUNDLE_CHALLENGE_SOLVED = "bundle_challenge_solved";
    public static final String BUNDLE_CHALLENGE_OPPONENT_MOVE = "bundle_challenge_opponent_move";
    public static final String BUNDLE_CHALLENGE_QUERY = "bundle_challenge_query";
    public static final String BUNDLE_CHALLENGES_BACK_MAP = "bundle_challenges_back_map";
    //public static final String BUNDLE_COUNTRYSELECTION_BACK_MAP = "bundle_countryselection_back_map";
    public static final String NOTIFICATION_TITLE_BEEN_CHALLENGED = "¡Te han retado!";

    //Facebook
    public static final String FACEBOOK_REWARD_LIKE_FANPAGE = "250";
    public static final String FACEBOOK_REWARD_SHARE_PLAYER = "100";
    public static final java.lang.String FACEBOOK_FANPAGE_GRAPH_PATH = "/RecarGOGame/likes";
    public static final String FACEBOOK_FANPAGE_URL = "https://www.facebook.com/RecarGOGame/";
    public static final String FACEBOOK_PLAYER_URL = "http://recar-go.com/share/FreeCoins";
    public static final String BUNDLE_SOUVENIRS_GROUP_SELCTED = "bundle_souvenirs_group_selcted";

    //Google Auth
    public static final String GOOGLE_OAUTH_CLIENT_ID = "764736593889-r30kkh6m26khvn8l8kcdckrvp1e593ol.apps.googleusercontent.com";

    public static final String ERA_WORLDCUP_NAME = "Futbolística";
    public static final int MARKER_MAX_WIDTH_DP_SIZE = 50;
    public static final int MARKER_MAX_HEIGHT_DP_SIZE = 44;
    public static final String ACHV_GOLD_COLLECTOR = "Recolector de Oro";
    public static final String ACHV_SOUV_COLLECTOR = "Coleccionista de Souvenir";
    public static final String ACHV_PRIZE_WINNER = "Ganador de Premios";
    public static final String FACEBOOK = "Facebook";
    public static final String GOOGLE = "Google";
    public static final String LOCAL = "Local";

    public static String PLATFORM = "ANDROID";

    //TODO: Cambiar para obligar al cambio de era
    public static double RESELECT_ERA_IN_VERSION = 1.55;

    public enum ChallengeQuery
    {
        CREATE, UPDATE, SELECT
    }


    public enum FacebookActions
    {
        SHARE_PAGE, SHARE_PROFILE
    }

    public enum ChallengesBackStack
    {
        MAP, PROFILE, MAIN
    }
    public enum StoreNavigationStack
    {
        SOUVENIRS, SOUVENIRS_GROUPS, MAIN
    }

    public enum CombosNavigationStack
    {
        SOUVENIRs_GROUPED, SOUVENIRS
    }

    //Auth
    public static final String  INTENT_BUNDLE_AUTH_TYPE = "intent_bundle_auth_type";
}
