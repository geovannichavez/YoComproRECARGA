package com.globalpaysolutions.yocomprorecarga.interactors;

import android.graphics.Bitmap;

import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.AgesListModel;
import com.globalpaysolutions.yocomprorecarga.models.api.EraSelectionResponse;

import java.util.List;

/**
 * Created by Josué Chávez on 09/11/2017.
 */

public interface ErasListener
{
    void onRetrieveSuccess(List<AgesListModel> eras);
    void onRetrieveError(int pCodeStatus, Throwable pThrowable, String pRequiredVersion);
    void onEraSelectionSuccess(EraSelectionResponse eraSelection, String destiny);
    void onEraSelectionError(int pCodeStatus, Throwable pThrowable, SimpleResponse simpleResponse, String pRequiredVersion);
    void onRetrieveBitmapSuccess(Bitmap bitmap, String name, EraSelectionResponse eraSelection, String destiny, int value);
}
