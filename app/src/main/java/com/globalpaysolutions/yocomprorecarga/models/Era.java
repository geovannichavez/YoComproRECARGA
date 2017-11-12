package com.globalpaysolutions.yocomprorecarga.models;

/**
 * Created by Josué Chávez on 07/11/2017.
 */

public class Era
{
    private int EraID;
    private int Status;
    private String EraName;
    private String IconImageUrl;
    private String MainImageUrl;

    public Era()
    {

    }

    public int getEraID()
    {
        return EraID;
    }

    public int getStatus()
    {
        return Status;
    }

    public String getEraName()
    {
        return EraName;
    }

    public String getIconImageUrl()
    {
        return IconImageUrl;
    }

    public String getMainImageUrl()
    {
        return MainImageUrl;
    }

    public void setEraID(int eraID)
    {
        EraID = eraID;
    }

    public void setStatus(int status)
    {
        Status = status;
    }

    public void setEraName(String eraName)
    {
        EraName = eraName;
    }

    public void setIconImageUrl(String iconImageUrl)
    {
        IconImageUrl = iconImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl)
    {
        MainImageUrl = mainImageUrl;
    }
}
