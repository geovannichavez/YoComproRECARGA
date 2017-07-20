package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Josué Chávez on 20/07/2017.
 */

public class PrizesHistoryResponse
{
    @SerializedName("data")
    @Expose
    private List<PrizeData> data = null;

    public List<PrizeData> getData()
    {
        return data;
    }

    public void setData(List<PrizeData> data)
    {
        this.data = data;
    }
}
