package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RedeemSponsorPrizeReqBody
{
    @SerializedName("SponsorID")
    @Expose
    private int sponsorID;
    @SerializedName("Type")
    @Expose
    private int type;

    public int getSponsorID()
    {
        return sponsorID;
    }

    public void setSponsorID(int sponsorID)
    {
        this.sponsorID = sponsorID;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

}
