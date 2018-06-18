package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RedeemedPrizeReqBody
{
    @SerializedName("PrizeID")
    @Expose
    private int prizeID;
    @SerializedName("Redeemed")
    @Expose
    private boolean redeemed;

    public int getPrizeID()
    {
        return prizeID;
    }

    public void setPrizeID(int prizeID)
    {
        this.prizeID = prizeID;
    }

    public boolean isRedeemed()
    {
        return redeemed;
    }

    public void setRedeemed(boolean redeemed)
    {
        this.redeemed = redeemed;
    }
}
