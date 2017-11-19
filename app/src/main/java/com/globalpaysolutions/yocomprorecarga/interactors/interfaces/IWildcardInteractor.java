package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.globalpaysolutions.yocomprorecarga.interactors.WildcardListener;
import com.globalpaysolutions.yocomprorecarga.models.api.Tracking;

/**
 * Created by Josué Chávez on 18/11/2017.
 */

public interface IWildcardInteractor
{
    void exchangeWildcard(String pFirebaseID, int eraID, WildcardListener listener);
    void saveUserTracking(Tracking tracking);
}
