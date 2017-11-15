package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Josué Chávez on 15/11/2017.
 */

public class EraSelectionResponse
{
    @SerializedName("AgeID")
    @Expose
    private Integer ageID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("IconImage")
    @Expose
    private String iconImage;
    @SerializedName("ImageCollection")
    @Expose
    private List<ImageCollection> imageCollection = null;

    public Integer getAgeID()
    {
        return ageID;
    }

    public void setAgeID(Integer ageID)
    {
        this.ageID = ageID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getIconImage()
    {
        return iconImage;
    }

    public void setIconImage(String iconImage)
    {
        this.iconImage = iconImage;
    }

    public List<ImageCollection> getImageCollection()
    {
        return imageCollection;
    }

    public void setImageCollection(List<ImageCollection> imageCollection)
    {
        this.imageCollection = imageCollection;
    }
}
