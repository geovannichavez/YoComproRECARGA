package com.globalpaysolutions.yocomprorecarga.models;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Josué Chávez on 13/01/2017.
 */

public class Country
{
    private String code;
    private String name;
    private String countrycode;
    private String PhoneCode;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCountrycode()
    {
        return countrycode;
    }

    public void setCountrycode(String countrycode)
    {
        this.countrycode = countrycode;
    }

    public String getPhoneCode()
    {
        return PhoneCode;
    }

    public void setPhoneCode(String phoneCode)
    {
        this.PhoneCode = phoneCode;
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

