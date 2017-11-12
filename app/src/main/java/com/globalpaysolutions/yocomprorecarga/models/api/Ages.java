package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Josué Chávez on 09/11/2017.
 */

public class Ages
{
    @SerializedName("agesListModel")
    @Expose
    private List<AgesListModel> agesListModel = null;

    public List<AgesListModel> getAgesListModel()
    {
        return agesListModel;
    }

    public void setAgesListModel(List<AgesListModel> agesListModel)
    {
        this.agesListModel = agesListModel;
    }
}
