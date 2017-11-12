package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 09/11/2017.
 */

public class AgesResponse
{

    @SerializedName("ages")
    @Expose
    private Ages ages;
    @SerializedName("count")
    @Expose
    private Integer count;

    public Ages getAges()
    {
        return ages;
    }

    public void setAges(Ages ages)
    {
        this.ages = ages;
    }

    public Integer getCount()
    {
        return count;
    }

    public void setCount(Integer count)
    {
        this.count = count;
    }
}
