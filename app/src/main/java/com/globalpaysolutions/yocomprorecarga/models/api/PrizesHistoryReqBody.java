package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrizesHistoryReqBody
{
    @SerializedName("Option")
    @Expose
    private int option;
    @SerializedName("CategoryID")
    @Expose
    private int categoryID;

    public int getOption()
    {
        return option;
    }

    public void setOption(int option)
    {
        this.option = option;
    }

    public int getCategoryID()
    {
        return categoryID;
    }

    public void setCategoryID(int categoryID)
    {
        this.categoryID = categoryID;
    }
}