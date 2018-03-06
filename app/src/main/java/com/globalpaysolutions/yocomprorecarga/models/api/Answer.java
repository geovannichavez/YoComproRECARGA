package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 6/3/2018.
 */

public class Answer
{
    @SerializedName("Answer")
    @Expose
    private String answer;

    @SerializedName("TriviaAnswerID")
    @Expose
    private int triviaAnswerID;

    public String getAnswer()
    {
        return answer;
    }

    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    public int getTriviaAnswerID()
    {
        return triviaAnswerID;
    }

    public void setTriviaAnswerID(int triviaAnswerID)
    {
        this.triviaAnswerID = triviaAnswerID;
    }
}
