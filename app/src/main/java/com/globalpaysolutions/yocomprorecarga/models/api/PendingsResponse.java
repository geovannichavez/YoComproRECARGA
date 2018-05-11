package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 12/3/2018.
 */

public class PendingsResponse
{
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("GetNewTrivia")
    @Expose
    private int getNewTrivia;

    @SerializedName("NewAge")
    @Expose
    private int newAge;


    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public int getGetNewTrivia()
    {
        return getNewTrivia;
    }

    public void setGetNewTrivia(int getNewTrivia)
    {
        this.getNewTrivia = getNewTrivia;
    }

    public int getNewAge()
    {
        return newAge;
    }

    public void setNewAge(int newAge)
    { this.newAge = newAge; }

}
