package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 20/3/2018.
 */

public class RequestRewardReq
{
    @SerializedName("ActionID")
    @Expose
    private int actionID;

    public int getActionID()
    {
        return actionID;
    }

    public void setActionID(int actionID)
    {
        this.actionID = actionID;
    }
}
