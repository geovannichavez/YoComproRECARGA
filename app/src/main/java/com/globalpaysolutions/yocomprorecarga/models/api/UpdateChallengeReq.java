package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 18/2/2018.
 */

public class UpdateChallengeReq
{

    @SerializedName("SelectOption")
    @Expose
    private int selectOption;
    @SerializedName("ChallengeID")
    @Expose
    private int challengeID;

    public int getSelectOption()
    {
        return selectOption;
    }

    public void setSelectOption(int selectOption)
    {
        this.selectOption = selectOption;
    }

    public int getChallengeID()
    {
        return challengeID;
    }

    public void setChallengeID(int challengeID)
    {
        this.challengeID = challengeID;
    }
}
