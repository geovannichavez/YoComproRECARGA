package com.globalpaysolutions.yocomprorecarga.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Josué Chávez on 22/07/2017.
 */

public class ChestData2D
{
    private int chestType;
    private LatLng location;
    private int sponsorID;
    private int exchangeType;

    public ChestData2D()
    {

    }

    public int getChestType()
    {
        return chestType;
    }

    public int getSponsorID()
    {
        return sponsorID;
    }

    public int getExchangeType()
    {
        return exchangeType;
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

    public void setSponsorID(int sponsorID)
    {
        this.sponsorID = sponsorID;
    }

    public void setExchangeType(int exchangeType)
    {
        this.exchangeType = exchangeType;
    }
}
