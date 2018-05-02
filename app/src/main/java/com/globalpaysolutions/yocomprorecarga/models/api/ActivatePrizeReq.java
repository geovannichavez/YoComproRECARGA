package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 22/11/2017.
 */

public class ActivatePrizeReq
{
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("PIN")
    @Expose
    private String pIN;

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPIN()
    {
        return pIN;
    }

    public void setPIN(String pIN)
    {
        this.pIN = pIN;
    }
}
