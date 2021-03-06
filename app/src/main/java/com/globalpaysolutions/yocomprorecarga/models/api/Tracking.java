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
    private int totalWinCoins;
    @SerializedName("TotalWinPrizes")
    @Expose
    private int totalWinPrizes;
    @SerializedName("CurrentCoinsProgress")
    @Expose
    private int currentCoinsProgress;
    @SerializedName("TotalSouvenirs")
    @Expose
    private int totalSouvenirs;
    @SerializedName("AgeID")
    @Expose
    private int ageID;
    @SerializedName("Nickname")
    @Expose
    private String nickname;
    @SerializedName("CountryID")
    @Expose
    private int countryID;
    @SerializedName("CountryName")
    @Expose
    private String countryName;
    @SerializedName("UrlImg")
    @Expose
    private String urlImg;
    @SerializedName("UrlImgMarker")
    @Expose
    private String urlImgMarker;

    public int getTotalWinCoins()
    {
        return totalWinCoins;
    }

    public void setTotalWinCoins(int totalWinCoins)
    {
        this.totalWinCoins = totalWinCoins;
    }

    public int getTotalWinPrizes()
    {
        return totalWinPrizes;
    }

    public void setTotalWinPrizes(int totalWinPrizes)
    {
        this.totalWinPrizes = totalWinPrizes;
    }

    public int getCurrentCoinsProgress()
    {
        return currentCoinsProgress;
    }

    public void setCurrentCoinsProgress(int currentCoinsProgress)
    {
        this.currentCoinsProgress = currentCoinsProgress;
    }

    public int getTotalSouvenirs()
    {
        return totalSouvenirs;
    }

    public void setTotalSouvenirs(int totalSouvenirs)
    {
        this.totalSouvenirs = totalSouvenirs;
    }

    public int getAgeID()
    {
        return ageID;
    }

    public void setAgeID(int ageID)
    {
        this.ageID = ageID;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public int getCountryID()
    {
        return countryID;
    }

    public void setCountryID(int countryID)
    {
        this.countryID = countryID;
    }

    public String getCountryName()
    {
        return countryName;
    }

    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }

    public String getUrlImg()
    {
        return urlImg;
    }

    public void setUrlImg(String urlImg)
    {
        this.urlImg = urlImg;
    }

    public String getUrlImgMarker()
    {
        return urlImgMarker;
    }

    public void setUrlImgMarker(String urlImgMarker)
    {
        this.urlImgMarker = urlImgMarker;
    }

}
