package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 16/2/2018.
 */

public class CreateChallengeReq
{
    @SerializedName("Bet")
    @Expose
    private double bet;
    @SerializedName("SelectOption")
    @Expose
    private int selectOption;
    @SerializedName("OpponentID")
    @Expose
    private String opponentID;

    public double getBet()
    {
        return bet;
    }

    public void setBet(double bet)
    {
        this.bet = bet;
    }

    public int getSelectOption()
    {
        return selectOption;
    }

    public void setSelectOption(int selectOption)
    {
        this.selectOption = selectOption;
    }

    public String getOpponentID()
    {
        return opponentID;
    }

    public void setOpponentID(String opponentID)
    {
        this.opponentID = opponentID;
    }
}
