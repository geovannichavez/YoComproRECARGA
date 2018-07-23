package com.globalpaysolutions.yocomprorecarga.models;

/**
 * Created by Josué Chávez on 20/07/2017.
 */

public class Prize
{
    private String regDate;
    private String code;
    private String title;
    private String description;
    private String dialNumberOrPlace;
    private int level;
    private boolean redeemedPrize;
    private int winPrizeID;
    private String backgroundPrizeHistory;
    private String backgroundWinPrize;
    private String urlLogo;

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

    public String getBackgroundPrizeHistory()
    {
        return backgroundPrizeHistory;
    }

    public void setBackgroundPrizeHistory(String backgroundPrizeHistory)
    {
        this.backgroundPrizeHistory = backgroundPrizeHistory;
    }

    public String getBackgroundWinPrize()
    {
        return backgroundWinPrize;
    }

    public void setBackgroundWinPrize(String backgroundWinPrize)
    {
        this.backgroundWinPrize = backgroundWinPrize;
    }

    public String getUrlLogo()
    {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo)
    {
        this.urlLogo = urlLogo;
    }

}
