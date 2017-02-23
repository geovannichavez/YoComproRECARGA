package com.globalpaysolutions.yocomprorecarga.models.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josué Chávez on 23/02/2017.
 */

public class RegisterConsumerReqBody
{
    private String consumerMsisdn;
    private String countryId;
    private String deviceId;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getConsumerMsisdn()
    {
        return consumerMsisdn;
    }

    public void setConsumerMsisdn(String consumerMsisdn)
    {
        this.consumerMsisdn = consumerMsisdn;
    }

    public String getCountryId()
    {
        return countryId;
    }

    public void setCountryId(String countryId)
    {
        this.countryId = countryId;
    }

    public String getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
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
