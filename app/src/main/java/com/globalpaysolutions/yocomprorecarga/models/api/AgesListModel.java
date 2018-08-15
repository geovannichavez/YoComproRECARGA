package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 09/11/2017.
 */

public class AgesListModel
{
    @SerializedName("ageID")
    @Expose
    private int ageID;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("iconImage")
    @Expose
    private String iconImage;
    @SerializedName("mainImage")
    @Expose
    private String mainImage;
    @SerializedName("RequiredSouvenir")
    @Expose
    private int requiredSouvenir;

    public int getAgeID()
    {
        return ageID;
    }

    public void setAgeID(int ageID)
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

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getIconImage()
    {
        return iconImage;
    }

    public void setIconImage(String iconImage)
    {
        this.iconImage = iconImage;
    }

    public String getMainImage()
    {
        return mainImage;
    }

    public void setMainImage(String mainImage)
    {
        this.mainImage = mainImage;
    }

    public int getRequiredSouvenir()
    {
        return requiredSouvenir;
    }

    public void setRequiredSouvenir(int requiredSouvenir)
    {
        this.requiredSouvenir = requiredSouvenir;
    }


}
