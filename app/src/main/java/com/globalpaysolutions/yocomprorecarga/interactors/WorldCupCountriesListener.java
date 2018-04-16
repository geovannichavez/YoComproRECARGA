package com.globalpaysolutions.yocomprorecarga.interactors;

import android.graphics.Bitmap;

import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.CountrySelectedResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.WorldCupCountriesRspns;

/**
 * Created by Josué Chávez on 13/4/2018.
 */

public interface WorldCupCountriesListener
{
    void onRetrieveSuccess(WorldCupCountriesRspns response);
    void onRetrieveError(int codeStatus, Throwable throwable, SimpleResponse response);
    void onSelectedSuccess(CountrySelectedResponse response);
    void onSelectedError(int code, Throwable t, SimpleResponse errorResponse);
    void onUpdateMarkerUrl();
    //void onRetrieveBitmapSuccess(Bitmap bitmap);

}
