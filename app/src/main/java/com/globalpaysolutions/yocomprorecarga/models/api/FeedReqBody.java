package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedReqBody
{
    @SerializedName("Option")
    @Expose
    private int option;

    public int getOption()
    {
        return option;
    }

    public void setOption(int option)
    {
        this.option = option;
    }

}
