package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Josué Chávez on 13/4/2018.
 */

public class WorldCupCountriesRspns
{
    @SerializedName("Country")
    @Expose
    private List<Country> country = null;

    public List<Country> getCountry()
    {
        return country;
    }

    public void setCountry(List<Country> country)
    {
        this.country = country;
    }

}
