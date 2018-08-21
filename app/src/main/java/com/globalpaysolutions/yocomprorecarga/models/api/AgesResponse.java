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
    private int count;
    @SerializedName("TotalSouvenir")
    @Expose
    private int totalSouvenir;

    public Ages getAges()
    {
        return ages;
    }

    public void setAges(Ages ages)
    {
        this.ages = ages;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public int getTotalSouvenir()
    {
        return totalSouvenir;
    }

    public void setTotalSouvenir(int totalSouvenir)
    {
        this.totalSouvenir = totalSouvenir;
    }
}
