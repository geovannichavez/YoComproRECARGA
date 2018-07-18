package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 18/11/2017.
 */

public class ExchangeWildcardResponse
{
    @SerializedName("Type")
    @Expose
    private int type;
    @SerializedName("ExchangeCoins")
    @Expose
    private int exchangeCoins;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("logoUrl")
    @Expose
    private String logoUrl;
    @SerializedName("HexColor")
    @Expose
    private String hexColor;
    @SerializedName("RGBColor")
    @Expose
    private String rGBColor;
    @SerializedName("Dial")
    @Expose
    private String dial;
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("PrizeLevel")
    @Expose
    private int prizeLevel;
    @SerializedName("tracking")
    @Expose
    private Tracking tracking;
    @SerializedName("Achievement")
    @Expose
    private Achievement achievement;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Message")
    @Expose
    private Object message;
    @SerializedName("WaitTime")
    @Expose
    private Object waitTime;
    @SerializedName("UrlBackground")
    @Expose
    private String urlBackground;

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public int getExchangeCoins()
    {
        return exchangeCoins;
    }

    public void setExchangeCoins(int exchangeCoins)
    {
        this.exchangeCoins = exchangeCoins;
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

    public String getLogoUrl()
    {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl)
    {
        this.logoUrl = logoUrl;
    }

    public String getHexColor()
    {
        return hexColor;
    }

    public void setHexColor(String hexColor)
    {
        this.hexColor = hexColor;
    }

    public String getRGBColor()
    {
        return rGBColor;
    }

    public void setRGBColor(String rGBColor)
    {
        this.rGBColor = rGBColor;
    }

    public String getDial()
    {
        return dial;
    }

    public void setDial(String dial)
    {
        this.dial = dial;
    }

    public String getResponseCode()
    {
        return responseCode;
    }

    public void setResponseCode(String responseCode)
    {
        this.responseCode = responseCode;
    }

    public int getPrizeLevel()
    {
        return prizeLevel;
    }

    public void setPrizeLevel(int prizeLevel)
    {
        this.prizeLevel = prizeLevel;
    }

    public Tracking getTracking()
    {
        return tracking;
    }

    public void setTracking(Tracking tracking)
    {
        this.tracking = tracking;
    }

    public Achievement getAchievement()
    {
        return achievement;
    }

    public void setAchievement(Achievement achievement)
    {
        this.achievement = achievement;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public Object getMessage()
    {
        return message;
    }

    public void setMessage(Object message)
    {
        this.message = message;
    }

    public Object getWaitTime()
    {
        return waitTime;
    }

    public void setWaitTime(Object waitTime)
    {
        this.waitTime = waitTime;
    }

    public String getUrlBackground()
    {
        return urlBackground;
    }

    public void setUrlBackground(String urlBackground)
    {
        this.urlBackground = urlBackground;
    }
}
