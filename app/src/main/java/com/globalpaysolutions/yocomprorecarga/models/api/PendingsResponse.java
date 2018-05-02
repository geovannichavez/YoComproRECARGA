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
}
