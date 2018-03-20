package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 20/3/2018.
 */

public class RewardResponse
{
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("Coins")
    @Expose
    private int coins;
    @SerializedName("Message")
    @Expose
    private String message;

    public String getResponseCode()
    {
        return responseCode;
    }

    public void setResponseCode(String responseCode)
    {
        this.responseCode = responseCode;
    }

    public int getCoins()
    {
        return coins;
    }

    public void setCoins(int coins)
    {
        this.coins = coins;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

}
