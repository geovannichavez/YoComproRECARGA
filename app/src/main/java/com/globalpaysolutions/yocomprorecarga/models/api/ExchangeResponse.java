package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 11/05/2017.
 */

public class ExchangeResponse
{
    @SerializedName("ExchangeCoins")
    @Expose
    private Integer exchangeCoins;
    @SerializedName("tracking")
    @Expose
    private Tracking tracking;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Message")
    @Expose
    private String message;

    public Integer getExchangeCoins()
    {
        return exchangeCoins;
    }

    public void setExchangeCoins(Integer exchangeCoins)
    {
        this.exchangeCoins = exchangeCoins;
    }

    public Tracking getTracking()
    {
        return tracking;
    }

    public void setTracking(Tracking tracking)
    {
        this.tracking = tracking;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
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
