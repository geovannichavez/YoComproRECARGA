package com.globalpaysolutions.yocomprorecarga.models.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josué Chávez on 11/05/2017.
 */

public class ExchangeReqBody
{
    private Integer consumerID;
    private String locationID;
    private Double longitude;
    private Double latitude;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getConsumerID()
    {
        return consumerID;
    }

    public void setConsumerID(Integer consumerID)
    {
        this.consumerID = consumerID;
    }

    public String getLocationID()
    {
        return locationID;
    }

    public void setLocationID(String locationID)
    {
        this.locationID = locationID;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(Double longitude)
    {
        this.longitude = longitude;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(Double latitude)
    {
        this.latitude = latitude;
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
