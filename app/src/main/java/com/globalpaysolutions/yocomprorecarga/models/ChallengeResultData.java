package com.globalpaysolutions.yocomprorecarga.models;

import java.io.Serializable;

/**
 * Created by Josué Chávez on 19/2/2018.
 */

public class ChallengeResultData implements Serializable
{
    private String mResultTitle;
    private String mResultContent;
    private String mPlayerMoveIcon;
    private String mOppnenteMoveIcon;
    private String mOpponentNickname;
    private int mPlayerSelection;
    private int mOppnentSelection;
    private int mBet;
    private int mOverallResult;

    public String getResultTitle()
    {
        return mResultTitle;
    }

    public void setResultTitle(String mResultTitle)
    {
        this.mResultTitle = mResultTitle;
    }

    public String getResultContent()
    {
        return mResultContent;
    }

    public void setResultContent(String mResultContent)
    {
        this.mResultContent = mResultContent;
    }

    public String getPlayerMoveIcon()
    {
        return mPlayerMoveIcon;
    }

    public void setPlayerMoveIcon(String mPlayerMoveIcon)
    {
        this.mPlayerMoveIcon = mPlayerMoveIcon;
    }

    public String getOppnenteMoveIcon()
    {
        return mOppnenteMoveIcon;
    }

    public void setOppnenteMoveIcon(String mOppnenteMoveIcon)
    {
        this.mOppnenteMoveIcon = mOppnenteMoveIcon;
    }

    public int getPlayerSelection()
    {
        return mPlayerSelection;
    }

    public void setPlayerSelection(int mPlayerSelection)
    {
        this.mPlayerSelection = mPlayerSelection;
    }

    public int getOppnentSelection()
    {
        return mOppnentSelection;
    }

    public void setOppnentSelection(int mOppnentSelection)
    {
        this.mOppnentSelection = mOppnentSelection;
    }

    public int getBet()
    {
        return mBet;
    }

    public void setBet(int mBet)
    {
        this.mBet = mBet;
    }

    public int getOverallResult()
    {
        return mOverallResult;
    }

    public void setOverallResult(int mOverallResult)
    {
        this.mOverallResult = mOverallResult;
    }

    public String getOpponentNickname()
    {
        return mOpponentNickname;
    }

    public void setOpponentNickname(String mOpponentNickname)
    {
        this.mOpponentNickname = mOpponentNickname;
    }
}
