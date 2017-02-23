package com.globalpaysolutions.yocomprorecarga.models.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josué Chávez on 23/02/2017.
 */

public class RegisterClientResponse
{
    private Boolean result;
    private Integer consumerID;
    private String message;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Boolean getResult()
    {
        return result;
    }

    public void setResult(Boolean result)
    {
        this.result = result;
    }

    public Integer getConsumerID()
    {
        return consumerID;
    }

    public void setConsumerID(Integer consumerID)
    {
        this.consumerID = consumerID;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
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
