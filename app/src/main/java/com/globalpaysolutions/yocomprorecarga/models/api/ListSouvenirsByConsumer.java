package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 10/11/2017.
 */

public class ListSouvenirsByConsumer
{
    @SerializedName("SouvenirID")
    @Expose
    private Integer souvenirID;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("AgeID")
    @Expose
    private Integer ageID;
    @SerializedName("ImgUrl")
    @Expose
    private String imgUrl;
    @SerializedName("Level")
    @Expose
    private Integer level;
    @SerializedName("SouvenirsOwnedByConsumer")
    @Expose
    private Integer souvenirsOwnedByConsumer;

    public Integer getSouvenirID()
    {
        return souvenirID;
    }

    public void setSouvenirID(Integer souvenirID)
    {
        this.souvenirID = souvenirID;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Integer getAgeID()
    {
        return ageID;
    }

    public void setAgeID(Integer ageID)
    {
        this.ageID = ageID;
    }

    public String getImgUrl()
    {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }

    public Integer getLevel()
    {
        return level;
    }

    public void setLevel(Integer level)
    {
        this.level = level;
    }

    public Integer getSouvenirsOwnedByConsumer()
    {
        return souvenirsOwnedByConsumer;
    }

    public void setSouvenirsOwnedByConsumer(Integer souvenirsOwnedByConsumer)
    {
        this.souvenirsOwnedByConsumer = souvenirsOwnedByConsumer;
    }
}
