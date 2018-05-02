package com.globalpaysolutions.yocomprorecarga.models;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Josué Chávez on 13/01/2017.
 */

public class Country
{
    private String Code;
    private String Name;
    private String CountryCode;
    private String PhoneCode;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getCode()
    {
        return Code;
    }

    public void setCode(String code)
    {
        this.Code = code;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String name)
    {
        this.Name = name;
    }

    public String getCountrycode()
    {
        return CountryCode;
    }

    public void setCountrycode(String countrycode)
    {
        this.CountryCode = countrycode;
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

