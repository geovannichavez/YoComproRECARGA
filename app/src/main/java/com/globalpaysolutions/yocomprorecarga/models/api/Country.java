package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 13/4/2018.
 */

public class Country
{
    @SerializedName("WorldCupCountryID")
    @Expose
    private int worldCupCountryID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("UrlImg")
    @Expose
    private String urlImg;
    @SerializedName("UrlImgMarker")
    @Expose
    private String urlImgMarker;

    public int getWorldCupCountryID()
    {
        return worldCupCountryID;
    }

    public void setWorldCupCountryID(int worldCupCountryID)
    {
        this.worldCupCountryID = worldCupCountryID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUrlImg()
    {
        return urlImg;
    }

    public void setUrlImg(String urlImg)
    {
        this.urlImg = urlImg;
    }

    public String getUrlImgMarker()
    {
        return urlImgMarker;
    }

    public void setUrlImgMarker(String urlImgMarker)
    {
        this.urlImgMarker = urlImgMarker;
    }
}
