package com.globalpaysolutions.yocomprorecarga.models.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josué Chávez on 06/04/2017.
 */

public class StoreAirtimeReportReqBody
{
    private String storeName;
    private String addressStore;
    private double longitude;
    private double latitude;
    private String firebaseID;
    private int consumerID;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getStoreName()
    {
        return storeName;
    }

    public void setStoreName(String storeName)
    {
        this.storeName = storeName;
    }

    public String getAddressStore()
    {
        return addressStore;
    }

    public void setAddressStore(String addressStore)
    {
        this.addressStore = addressStore;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public String getFirebaseID()
    {
        return firebaseID;
    }

    public void setFirebaseID(String firebaseID)
    {
        this.firebaseID = firebaseID;
    }

    public int getConsumerID()
    {
        return consumerID;
    }

    public void setConsumerID(int consumerID)
    {
        this.consumerID = consumerID;
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
