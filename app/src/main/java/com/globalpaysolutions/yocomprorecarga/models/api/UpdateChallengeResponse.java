package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 18/2/2018.
 */

public class UpdateChallengeResponse
{
    @SerializedName("result")
    @Expose
    private int result;
    @SerializedName("OpponentNickname")
    @Expose
    private String opponentNickname;
    @SerializedName("Bet")
    @Expose
    private int bet;
    @SerializedName("OpponentSelection")
    @Expose
    private int opponentSelection;
    @SerializedName("Selection")
    @Expose
    private int selection;

    public int getResult()
    {
        return result;
    }

    public void setResult(int result)
    {
        this.result = result;
    }

    public String getOpponentNickname()
    {
        return opponentNickname;
    }

    public void setOpponentNickname(String opponentNickname)
    {
        this.opponentNickname = opponentNickname;
    }

    public int getBet()
    {
        return bet;
    }

    public void setBet(int bet)
    {
        this.bet = bet;
    }

    public int getOpponentSelection()
    {
        return opponentSelection;
    }

    public void setOpponentSelection(int opponentSelection)
    {
        this.opponentSelection = opponentSelection;
    }

    public int getSelection()
    {
        return selection;
    }

    public void setSelection(int selection)
    {
        this.selection = selection;
    }
}
