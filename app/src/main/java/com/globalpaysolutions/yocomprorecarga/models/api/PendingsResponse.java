package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    @SerializedName("NewFeed")
    @Expose
    private int newFeed;
    @SerializedName("sponsor")
    @Expose
    private List<Sponsor> sponsor = null;

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
    {
        this.newAge = newAge;
    }

    public int getNewFeed()
    {
        return newFeed;
    }

    public void setNewFeed(int newFeed)
    {
        this.newFeed = newFeed;
    }

    public List<Sponsor> getSponsor()
    {
        return sponsor;
    }

    public void setSponsor(List<Sponsor> sponsor)
    {
        this.sponsor = sponsor;
    }

}
