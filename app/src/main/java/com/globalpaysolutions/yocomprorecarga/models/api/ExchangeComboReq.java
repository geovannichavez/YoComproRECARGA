package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 17/01/2018.
 */

public class ExchangeComboReq
{
    @SerializedName("ComboID")
    @Expose
    private int comboID;

    public int getComboID()
    {
        return comboID;
    }

    public void setComboID(int comboID)
    {
        this.comboID = comboID;
    }
}
