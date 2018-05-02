package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 16/01/2018.
 */

public class ComboSouvenir
{
    @SerializedName("ImgUrl")
    @Expose
    private String imgUrl;
    @SerializedName("Level")
    @Expose
    private int level;
    @SerializedName("Exchangeable")
    @Expose
    private int exchangeable;
    @SerializedName("Title")
    @Expose
    private String title;

    public String getImgUrl()
    {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getExchangeable()
    {
        return exchangeable;
    }

    public void setExchangeable(int exchangeable)
    {
        this.exchangeable = exchangeable;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

}
