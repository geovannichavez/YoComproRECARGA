package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sponsor
{
    @SerializedName("MarkerUrl")
    @Expose
    private String markerUrl;

    public String getMarkerUrl()
    {
        return markerUrl;
    }

    public void setMarkerUrl(String markerUrl)
    {
        this.markerUrl = markerUrl;
    }
}
