package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Josué Chávez on 12/11/2017.
 */

public class StoreItemsResponse
{
    @SerializedName("listGameStoreResponse")
    @Expose
    private List<ListGameStoreResponse> listGameStoreResponse = null;

    public List<ListGameStoreResponse> getListGameStoreResponse()
    {
        return listGameStoreResponse;
    }

    public void setListGameStoreResponse(List<ListGameStoreResponse> listGameStoreResponse)
    {
        this.listGameStoreResponse = listGameStoreResponse;
    }

}
