package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    @SerializedName("TotalSouvenirs")
    @Expose
    private Integer totalSouvenirs;
    @SerializedName("AgeID")
    @Expose
    private Integer ageID;

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

    public Integer getTotalSouvenirs()
    {
        return totalSouvenirs;
    }

    public void setTotalSouvenirs(Integer totalSouvenirs)
    {
        this.totalSouvenirs = totalSouvenirs;
    }

    public Integer getAgeID()
    {
        return ageID;
    }

    public void setAgeID(Integer ageID)
    {
        this.ageID = ageID;
    }
}
