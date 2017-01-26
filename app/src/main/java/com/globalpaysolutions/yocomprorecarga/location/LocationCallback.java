package com.globalpaysolutions.yocomprorecarga.location;

import android.location.Location;

/**
 * Created by Josué Chávez on 23/01/2017.
 */

public interface LocationCallback
{
    void onLocationChanged(Location location);
    void onLocationApiManagerConnected(Location location);
}
