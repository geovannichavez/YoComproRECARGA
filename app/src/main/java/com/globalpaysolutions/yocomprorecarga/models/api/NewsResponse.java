package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse
{
    @SerializedName("response")
    @Expose
    private List<New> response = null;

    public List<New> getResponse() {
        return response;
    }

    public void setResponse(List<New> response) {
        this.response = response;
    }
}
