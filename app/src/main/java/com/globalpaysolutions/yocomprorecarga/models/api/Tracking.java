package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josué Chávez on 12/05/2017.
 */

public class Tracking
{
    @SerializedName("TotalWinCoins")
    @Expose
    private Integer totalWinCoins;
    @SerializedName("TotalWinPrizes")
    @Expose
    private Integer totalWinPrizes;
    @SerializedName("CurrentCoinsProgress")
    @Expose
    private Integer currentCoinsProgress;

    public Integer getTotalWinCoins()
    {
        return totalWinCoins;
    }

    public void setTotalWinCoins(Integer totalWinCoins)
    {
        this.totalWinCoins = totalWinCoins;
    }

    public Integer getTotalWinPrizes()
    {
        return totalWinPrizes;
    }

    public void setTotalWinPrizes(Integer totalWinPrizes)
    {
        this.totalWinPrizes = totalWinPrizes;
    }

    public Integer getCurrentCoinsProgress()
    {
        return currentCoinsProgress;
    }

    public void setCurrentCoinsProgress(Integer currentCoinsProgress)
    {
        this.currentCoinsProgress = currentCoinsProgress;
    }

}
