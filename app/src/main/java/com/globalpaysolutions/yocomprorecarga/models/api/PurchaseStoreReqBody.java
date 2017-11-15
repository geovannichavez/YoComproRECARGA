package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 15/11/2017.
 */

public class PurchaseStoreReqBody
{
    @SerializedName("StoreId")
    @Expose
    private Integer storeId;

    public Integer getStoreId()
    {
        return storeId;
    }

    public void setStoreId(Integer storeId)
    {
        this.storeId = storeId;
    }
}
