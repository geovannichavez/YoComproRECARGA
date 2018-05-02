package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ChallengesResponse;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Josué Chávez on 8/2/2018.
 */

public interface ChallengesListener
{
    void onRetrieveSuccess(ChallengesResponse response);
    void onRetrieveError(int codeStatus, Throwable throwable, String requiredVersion, SimpleResponse errorResponse);
    void onPlayerDataInserted(String playerFacebookID, LatLng location);
    void onPlayerLocationSet(String key);
    void onPlayerLocationDeleted(String key);
}
