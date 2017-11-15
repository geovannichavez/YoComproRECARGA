package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 11/05/2017.
 */

public class ExchangeResponse
{
    @SerializedName("Type")
    @Expose
    private Integer type;
    @SerializedName("ExchangeCoins")
    @Expose
    private Integer exchangeCoins;
    @SerializedName("tracking")
    @Expose
    private Tracking tracking;
    @SerializedName("Achievement")
    @Expose
    private Achievement achievement;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("ImgUrl")
    @Expose
    private String imgUrl;
    @SerializedName("Value")
    @Expose
    private Integer value;

    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Message")
    @Expose
    private String message;

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
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

    public String getImgUrl()
    {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }

    public Integer getValue()
    {
        return value;
    }

    public void setValue(Integer value)
    {
        this.value = value;
    }


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

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }


}
