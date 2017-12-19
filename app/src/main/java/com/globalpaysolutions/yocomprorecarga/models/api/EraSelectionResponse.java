package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Josué Chávez on 15/11/2017.
 */

public class EraSelectionResponse
{
    @SerializedName("AgeID")
    @Expose
    private int ageID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("IconImage")
    @Expose
    private String iconImage;
    @SerializedName("MarkerG")
    @Expose
    private String markerG;
    @SerializedName("MarkerS")
    @Expose
    private String markerS;
    @SerializedName("MarkerB")
    @Expose
    private String markerB;
    @SerializedName("MarkerW")
    @Expose
    private String markerW;

    public int getAgeID()
    {
        return ageID;
    }

    public void setAgeID(int ageID)
    {
        this.ageID = ageID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getIconImage()
    {
        return iconImage;
    }

    public void setIconImage(String iconImage)
    {
        this.iconImage = iconImage;
    }

    public String getMarkerG()
    {
        return markerG;
    }

    public void setMarkerG(String markerG)
    {
        this.markerG = markerG;
    }

    public String getMarkerS()
    {
        return markerS;
    }

    public void setMarkerS(String markerS)
    {
        this.markerS = markerS;
    }

    public String getMarkerB()
    {
        return markerB;
    }

    public void setMarkerB(String markerB)
    {
        this.markerB = markerB;
    }

    public String getMarkerW()
    {
        return markerW;
    }

    public void setMarkerW(String markerW)
    {
        this.markerW = markerW;
    }

}
