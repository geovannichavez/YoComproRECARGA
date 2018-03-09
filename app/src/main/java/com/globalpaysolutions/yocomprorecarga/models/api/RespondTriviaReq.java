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

    public int getTriviaAnswerID()
    {
        return triviaAnswerID;
    }

    public void setTriviaAnswerID(int triviaAnswerID)
    {
        this.triviaAnswerID = triviaAnswerID;
    }
}
