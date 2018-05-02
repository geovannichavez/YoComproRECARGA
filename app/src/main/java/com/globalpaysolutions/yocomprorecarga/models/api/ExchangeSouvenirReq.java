package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 17/11/2017.
 */

public class ExchangeSouvenirReq
{
    @SerializedName("SouvenirID")
    @Expose
    private int souvenirID;

    public int getSouvenirID() {
        return souvenirID;
    }

    public void setSouvenirID(int souvenirID) {
        this.souvenirID = souvenirID;
    }
}
