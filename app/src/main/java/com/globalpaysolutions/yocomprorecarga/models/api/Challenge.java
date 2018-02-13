package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 13/2/2018.
 */

public class Challenge
{
    @SerializedName("Result")
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
    @SerializedName("OpponentID")
    @Expose
    private int opponentID;
    @SerializedName("Selection")
    @Expose
    private int selection;
    @SerializedName("Creator")
    @Expose
    private int creator;
    @SerializedName("Status")
    @Expose
    private int status;
    @SerializedName("ChallengeID")
    @Expose
    private int challengeID;
    @SerializedName("ConsumerID")
    @Expose
    private int consumerID;
    @SerializedName("RegDate")
    @Expose
    private String regDate;
    @SerializedName("Nickname")
    @Expose
    private String nickname;

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

    public int getOpponentID()
    {
        return opponentID;
    }

    public void setOpponentID(int opponentID)
    {
        this.opponentID = opponentID;
    }

    public int getSelection()
    {
        return selection;
    }

    public void setSelection(int selection)
    {
        this.selection = selection;
    }

    public int getCreator()
    {
        return creator;
    }

    public void setCreator(int creator)
    {
        this.creator = creator;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getChallengeID()
    {
        return challengeID;
    }

    public void setChallengeID(int challengeID)
    {
        this.challengeID = challengeID;
    }

    public int getConsumerID()
    {
        return consumerID;
    }

    public void setConsumerID(int consumerID)
    {
        this.consumerID = consumerID;
    }

    public String getRegDate()
    {
        return regDate;
    }

    public void setRegDate(String regDate)
    {
        this.regDate = regDate;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

}
