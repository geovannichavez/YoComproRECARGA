package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 12/11/2017.
 */

public class ListGameStoreResponse
{
    @SerializedName("StoreID")
    @Expose
    private Integer storeID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("ImgUrl")
    @Expose
    private String imgUrl;
    @SerializedName("Value")
    @Expose
    private Double value;

    public Integer getStoreID()
    {
        return storeID;
    }

    public void setStoreID(Integer storeID)
    {
        this.storeID = storeID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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

    public Double getValue()
    {
        return value;
    }

    public void setValue(Double value)
    {
        this.value = value;
    }
}
