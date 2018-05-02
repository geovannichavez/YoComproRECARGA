package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 15/4/2018.
 */

public class CountrySelectedReq
{
    @SerializedName("WorldCupCountryID")
    @Expose
    private int worldCupCountryID;

    public int getWorldCupCountryID() {
        return worldCupCountryID;
    }

    public void setWorldCupCountryID(int worldCupCountryID) {
        this.worldCupCountryID = worldCupCountryID;
    }
}
