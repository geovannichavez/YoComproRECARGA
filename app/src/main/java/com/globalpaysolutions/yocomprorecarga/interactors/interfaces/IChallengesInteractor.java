package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.firebase.geofire.GeoLocation;
import com.globalpaysolutions.yocomprorecarga.interactors.ChallengesListener;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Josué Chávez on 8/2/2018.
 */

public interface IChallengesInteractor
{
    void retrieveChallenges(ChallengesListener listener);
    void writePlayerDataLocation(LatLng location, ChallengesListener listener);
    void deleteCurrentUserLocation(String key, ChallengesListener listener);
    void setPlayerLocation(String playerFacebookID, GeoLocation geoLocation, ChallengesListener listener);
}
