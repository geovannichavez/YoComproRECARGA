package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 18/11/2017.
 */

public class ExchangeWildcardReq
{
    @SerializedName("LocationID")
    @Expose
    private String locationID;
    @SerializedName("AgeID")
    @Expose
    private int ageID;

    public String getLocationID()
    {
        return locationID;
    }

    public void setLocationID(String locationID)
    {
        this.locationID = locationID;
    }

    public int getAgeID()
    {
        return ageID;
    }

    public void setAgeID(int ageID)
    {
        this.ageID = ageID;
    }

}
