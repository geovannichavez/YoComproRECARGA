package com.globalpaysolutions.yocomprorecarga.models;

/**
 * Created by Josué Chávez on 15/2/2018.
 */

public class MarkerData
{
    private String mFirebaseID;
    private String mMarkerType;
    private String mTag;

    public MarkerData(String firebaseID, String markerType, String tag)
    {
        this.mFirebaseID = firebaseID;
        this.mMarkerType = markerType;
        this.mTag = tag;
    }

    public String getFirebaseID()
    {
        return mFirebaseID;
    }

    public String getMarkerType()
    {
        return mMarkerType;
    }

    public String getTag()
    {
        return mTag;
    }

    public void setFirebaseID(String mFirebaseID)
    {
        this.mFirebaseID = mFirebaseID;
    }

    public void setMarkerType(String mMarkerType)
    {
        this.mMarkerType = mMarkerType;
    }

    public void setTag(String mTag)
    {
        this.mTag = mTag;
    }

    @Override
    public String toString()
    {
        return super.toString();
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }
}
