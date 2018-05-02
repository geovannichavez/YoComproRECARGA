package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 15/4/2018.
 */

public class CountrySelectedResponse
{

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("WorldCupCountryID")
    @Expose
    private int worldCupCountryID;
    @SerializedName("UrlImg")
    @Expose
    private String urlImg;
    @SerializedName("UrlImgMarker")
    @Expose
    private String urlImgMarker;
    @SerializedName("Message")
    @Expose
    private String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWorldCupCountryID() {
        return worldCupCountryID;
    }

    public void setWorldCupCountryID(int worldCupCountryID) {
        this.worldCupCountryID = worldCupCountryID;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getUrlImgMarker() {
        return urlImgMarker;
    }

    public void setUrlImgMarker(String urlImgMarker) {
        this.urlImgMarker = urlImgMarker;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
