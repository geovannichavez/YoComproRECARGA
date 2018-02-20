package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Josué Chávez on 13/2/2018.
 */

public class ChallengesResponse
{
    @SerializedName("list")
    @Expose
    private List<Challenge> list = null;

    public List<Challenge> getList()
    {
        return list;
    }

    public void setList(List<Challenge> list)
    {
        this.list = list;
    }
}
