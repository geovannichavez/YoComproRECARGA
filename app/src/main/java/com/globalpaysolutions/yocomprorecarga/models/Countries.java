package com.globalpaysolutions.yocomprorecarga.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Josué Chávez on 13/01/2017.
 */

public class Countries
{

    private List<Country> countries = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<Country> getCountries()
    {
        return countries;
    }

    public void setCountries(List<Country> countries)
    {
        this.countries = countries;
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