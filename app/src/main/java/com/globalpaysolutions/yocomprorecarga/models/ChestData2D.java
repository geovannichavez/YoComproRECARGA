package com.globalpaysolutions.yocomprorecarga.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Josué Chávez on 22/07/2017.
 */

public class ChestData2D
{
    private int chestType;
    private LatLng location;

    public ChestData2D()
    {

    }

    public int getChestType()
    {
        return chestType;
    }

    public LatLng getLocation()
    {
        return location;
    }

    public void setChestType(int chestType)
    {
        this.chestType = chestType;
    }

    public void setLocation(LatLng location)
    {
        this.location = location;
    }
}
