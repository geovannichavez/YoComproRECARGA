package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josué Chávez on 11/05/2017.
 */

public class ExchangeReqBody
{

    @SerializedName("LocationID")
    @Expose
    private String locationID;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("ChestType")
    @Expose
    private Integer chestType;
    @SerializedName("AgeID")
    @Expose
    private Integer ageID;

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getChestType() {
        return chestType;
    }

    public void setChestType(Integer chestType) {
        this.chestType = chestType;
    }

    public Integer getAgeID() {
        return ageID;
    }

    public void setAgeID(Integer ageID) {
        this.ageID = ageID;
    }
}
