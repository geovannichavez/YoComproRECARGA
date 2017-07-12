package com.globalpaysolutions.yocomprorecarga.models.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josué Chávez on 23/02/2017.
 */

public class RegisterPhoneConsumerReqBody
{
    private String phone;
    private String countryID;
    private String deviceID;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getCountryID()
    {
        return countryID;
    }

    public void setCountryID(String countryID)
    {
        this.countryID = countryID;
    }

    public String getDeviceID()
    {
        return deviceID;
    }

    public void setDeviceID(String deviceID)
    {
        this.deviceID = deviceID;
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
