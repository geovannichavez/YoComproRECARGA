package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 12/3/2018.
 */

public class PendingsResponse
{
    @SerializedName("PendingChallenge")
    @Expose
    private int pendingChallenge;

    @SerializedName("GetNewTrivia")
    @Expose
    private int getNewTrivia;

    public int getPendingChallenge()
    {
        return pendingChallenge;
    }

    public void setPendingChallenge(int pendingChallenge)
    {
        this.pendingChallenge = pendingChallenge;
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
