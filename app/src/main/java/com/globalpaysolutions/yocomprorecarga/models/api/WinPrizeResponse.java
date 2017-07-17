package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 14/07/2017.
 */

public class WinPrizeResponse
{
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
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("PrizeLevel")
    @Expose
    private Integer prizeLevel;
    @SerializedName("tracking")
    @Expose
    private Tracking tracking;
    @SerializedName("WaitTime")
    @Expose
    private String waitTime;

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

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getResponseCode()
    {
        return responseCode;
    }

    public void setResponseCode(String responseCode)
    {
        this.responseCode = responseCode;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Integer getPrizeLevel()
    {
        return prizeLevel;
    }

    public void setPrizeLevel(Integer prizeLevel)
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

    public String getWaitTime()
    {
        return waitTime;
    }

    public void setWaitTime(String waitTime)
    {
        this.waitTime = waitTime;
    }
}
