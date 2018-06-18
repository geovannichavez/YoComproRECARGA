package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 20/07/2017.
 */

public class PrizeData
{
    @SerializedName("RegDate")
    @Expose
    private String regDate;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("DialNumberOrPlace")
    @Expose
    private String dialNumberOrPlace;
    @SerializedName("Level")
    @Expose
    private int level;
    @SerializedName("RedeemedPrize")
    @Expose
    private boolean redeemedPrize;
    @SerializedName("WinPrizeID")
    @Expose
    private int winPrizeID;

    public String getRegDate()
    {
        return regDate;
    }

    public void setRegDate(String regDate)
    {
        this.regDate = regDate;
    }

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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDialNumberOrPlace()
    {
        return dialNumberOrPlace;
    }

    public void setDialNumberOrPlace(String dialNumberOrPlace)
    {
        this.dialNumberOrPlace = dialNumberOrPlace;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public boolean isRedeemedPrize()
    {
        return redeemedPrize;
    }

    public void setRedeemedPrize(boolean redeemedPrize)
    {
        this.redeemedPrize = redeemedPrize;
    }

    public int getWinPrizeID()
    {
        return winPrizeID;
    }

    public void setWinPrizeID(int winPrizeID)
    {
        this.winPrizeID = winPrizeID;
    }

}
