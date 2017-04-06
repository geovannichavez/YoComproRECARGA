package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Josué Chávez on 29/03/2017.
 */

public interface ICapturePrizeARPresenter
{
    void initialize();
    void prizePointsQuery(LatLng pLocation);
    void updatePrizePntCriteria(LatLng pLocation);
    void setPOIClickListener();
    void _genericPOIAction(String pDisplayText);
}
