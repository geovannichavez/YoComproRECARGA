package com.globalpaysolutions.yocomprorecarga.models;

import java.util.HashMap;

/**
 * Created by Josué Chávez on 6/3/2018.
 */

public class QuestionTrivia
{
    private int mTriviaID;
    private String mTitle;
    private String mQuestionText;
    private String mSponsorUrl;
    private String mCoinsPrize;
    private int mPrizeType;
    private HashMap<Integer, String> mAnswers;

    public QuestionTrivia(){}

    public int getTriviaID()
    {
        return mTriviaID;
    }

     public void setTriviaID(int triviaID)
     {
         this.mTriviaID = triviaID;
     }

    public String getTitle()
    {
        return mTitle;
    }

    public void setTitle(String mTitle)
    {
        this.mTitle = mTitle;
    }

    public String getQuestionText()
    {
        return mQuestionText;
    }

    public void setQuestionText(String mQuestionText)
    {
        this.mQuestionText = mQuestionText;
    }

    public String getSponsorUrl()
    {
        return mSponsorUrl;
    }

    public void setSponsorUrl(String mSponsorUrl)
    {
        this.mSponsorUrl = mSponsorUrl;
    }

    public String getCoinsPrize()
    {
        return mCoinsPrize;
    }

    public void setCoinsPrize(String mCoinsPrize)
    {
        this.mCoinsPrize = mCoinsPrize;
    }

    public int getPrizeType()
    {
        return mPrizeType;
    }

    public void setPrizeType(int mPrizeType)
    {
        this.mPrizeType = mPrizeType;
    }

    public HashMap<Integer, String> getAnswers()
    {
        return mAnswers;
    }

    public void setAnswers(HashMap<Integer, String> mAnswers)
    {
        this.mAnswers = mAnswers;
    }
}
