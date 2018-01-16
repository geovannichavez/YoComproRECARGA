package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Josué Chávez on 16/01/2018.
 */

public class CombosResponse
{
    @SerializedName("response")
    @Expose
    private List<Combo> response = null;

    public List<Combo> getResponse() {
        return response;
    }

    public void setResponse(List<Combo> response) {
        this.response = response;
    }

}
