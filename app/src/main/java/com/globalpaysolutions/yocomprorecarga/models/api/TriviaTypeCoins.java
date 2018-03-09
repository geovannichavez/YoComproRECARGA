package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 8/3/2018.
 */

public class TriviaTypeCoins
{
    @SerializedName("ExchangeCoins")
    @Expose
    private int exchangeCoins;
    @SerializedName("TotalWinCoins")
    @Expose
    private int totalWinCoins;

    public int getExchangeCoins()
    {
        return exchangeCoins;
    }

    public void setExchangeCoins(int exchangeCoins)
    {
        this.exchangeCoins = exchangeCoins;
    }

    public int getTotalWinCoins()
    {
        return totalWinCoins;
    }

    public void setTotalWinCoins(int totalWinCoins)
    {
        this.totalWinCoins = totalWinCoins;
    }
}
