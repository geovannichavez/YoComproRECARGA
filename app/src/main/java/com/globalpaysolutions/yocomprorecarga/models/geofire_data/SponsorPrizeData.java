package com.globalpaysolutions.yocomprorecarga.models.geofire_data;

import java.util.HashMap;
import java.util.Map;

public class SponsorPrizeData
{
    private int visible;
    private String name;
    private String description;
    private String MarkerUrl;
    private String sponsorid;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public int getVisible()
    {
        return visible;
    }

    public void setVisible(int visible)
    {
        this.visible = visible;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getMarkerUrl()
    {
        return MarkerUrl;
    }

    public void setMarkerUrl(String markerUrl)
    {
        MarkerUrl = markerUrl;
    }

    public String getSponsorid()
    {
        return sponsorid;
    }

    public void setSponsorid(String sponsorid)
    {
        this.sponsorid = sponsorid;
    }

    public Map<String, Object> getAdditionalProperties()
    {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value)
    {
        this.additionalProperties.put(name, value);
    }


}
