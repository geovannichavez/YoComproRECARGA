package com.globalpaysolutions.yocomprorecarga.models.geofire_data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josué Chávez on 17/11/2017.
 */

public class WildcardYCRData
{
    private String brand;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
