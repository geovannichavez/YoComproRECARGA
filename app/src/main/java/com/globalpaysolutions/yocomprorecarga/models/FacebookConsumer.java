package com.globalpaysolutions.yocomprorecarga.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 05/07/2017.
 */

public class FacebookConsumer
{
    private String firstName;
    private String middleName;
    private String lastName;
    private String deviceID;
    private String uRL;
    private String email;
    private String profileID;
    private String userID;

    public FacebookConsumer()
    {
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getDeviceID()
    {
        return deviceID;
    }

    public void setDeviceID(String deviceID)
    {
        this.deviceID = deviceID;
    }

    public String getURL()
    {
        return uRL;
    }

    public void setURL(String uRL)
    {
        this.uRL = uRL;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getProfileID()
    {
        return profileID;
    }

    public void setProfileID(String profileID)
    {
        this.profileID = profileID;
    }

    public String getUserID()
    {
        return userID;
    }

    public void setUserID(String userID)
    {
        this.userID = userID;
    }
}
