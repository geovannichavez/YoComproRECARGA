package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 8/3/2018.
 */

public class TriviaTypePrize
{
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Dial")
    @Expose
    private String dial;
    @SerializedName("RGBColor")
    @Expose
    private String rGBColor;
    @SerializedName("HexColor")
    @Expose
    private String hexColor;
    @SerializedName("LogoUrl")
    @Expose
    private String logoUrl;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("TotalWinPrizes")
    @Expose
    private int totalWinPrizes;
    @SerializedName("PrizeLevel")
    @Expose
    private int prizeLevel;
    @SerializedName("TotalWinCoins")
    @Expose
    private int totalWinCoins;
    @SerializedName("CurrentCoinsProgress")
    @Expose
    private int currentCoinsProgress;
    @SerializedName("TotalSouvenirs")
    @Expose
    private int totalSouvenirs;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDial()
    {
        return dial;
    }

    public void setDial(String dial)
    {
        this.dial = dial;
    }

    public String getRGBColor()
    {
        return rGBColor;
    }

    public void setRGBColor(String rGBColor)
    {
        this.rGBColor = rGBColor;
    }

    public String getHexColor()
    {
        return hexColor;
    }

    public void setHexColor(String hexColor)
    {
        this.hexColor = hexColor;
    }

    public String getLogoUrl()
    {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl)
    {
        this.logoUrl = logoUrl;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getTotalWinPrizes()
    {
        return totalWinPrizes;
    }

    public void setTotalWinPrizes(int totalWinPrizes)
    {
        this.totalWinPrizes = totalWinPrizes;
    }

    public int getPrizeLevel()
    {
        return prizeLevel;
    }

    public void setPrizeLevel(int prizeLevel)
    {
        this.prizeLevel = prizeLevel;
    }

    public int getTotalWinCoins()
    {
        return totalWinCoins;
    }

    public void setTotalWinCoins(int totalWinCoins)
    {
        this.totalWinCoins = totalWinCoins;
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
}
