package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 8/3/2018.
 */

public class RespondTriviaReq
{
    @SerializedName("TriviaAnswerID")
    @Expose
    private int triviaAnswerID;

    @SerializedName("TriviaID")
    @Expose
    private int triviaID;

    public int getTriviaAnswerID()
    {
        return triviaAnswerID;
    }

    public void setTriviaAnswerID(int triviaAnswerID)
    {
        this.triviaAnswerID = triviaAnswerID;
    }

    public int getTriviaID()
    {
        return triviaID;
    }

    public void setTriviaID(int triviaID)
    {
        this.triviaID = triviaID;
    }
}
