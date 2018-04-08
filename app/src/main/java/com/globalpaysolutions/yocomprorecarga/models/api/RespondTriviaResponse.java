package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 8/3/2018.
 */

public class RespondTriviaResponse
{
    @SerializedName("Type")
    @Expose
    private int type;
    @SerializedName("Prize")
    @Expose
    private TriviaTypePrize prize;
    @SerializedName("Souvenir")
    @Expose
    private TriviaTypeSouvenir souvenir;
    @SerializedName("Coins")
    @Expose
    private TriviaTypeCoins coins;
    @SerializedName("HttpCode")
    @Expose
    private int httpCode;
    @SerializedName("InternalCode")
    @Expose
    private String internalCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("CorrectAnswerID")
    @Expose
    private int correctAnswerID;

    public void setType(int type)
    {
        this.type = type;
    }

    public int getType()
    {
        return type;
    }

    public void setPrize(TriviaTypePrize prize)
    {
        this.prize = prize;
    }

    public TriviaTypePrize getPrize()
    {
        return prize;
    }

    public void setSouvenir(TriviaTypeSouvenir souvenir)
    {
        this.souvenir = souvenir;
    }

    public TriviaTypeSouvenir getSouvenir()
    {
        return souvenir;
    }

    public void setCoins(TriviaTypeCoins coins)
    {
        this.coins = coins;
    }

    public TriviaTypeCoins getCoins()
    {
        return coins;
    }

    public int getHttpCode()
    {
        return httpCode;
    }

    public void setHttpCode(int httpCode)
    {
        this.httpCode = httpCode;
    }

    public String getInternalCode()
    {
        return internalCode;
    }

    public void setInternalCode(String internalCode)
    {
        this.internalCode = internalCode;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public int getCorrectAnswerID()
    {
        return correctAnswerID;
    }

    public void setCorrectAnswerID(int correctAnswerID)
    {
        this.correctAnswerID = correctAnswerID;
    }
}
