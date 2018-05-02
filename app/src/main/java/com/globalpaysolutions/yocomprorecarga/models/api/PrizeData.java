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
    private Integer level;

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

    public Integer getLevel()
    {
        return level;
    }

    public void setLevel(Integer level)
    {
        this.level = level;
    }

}
