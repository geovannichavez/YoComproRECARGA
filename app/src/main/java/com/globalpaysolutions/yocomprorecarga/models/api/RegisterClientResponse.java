package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josué Chávez on 23/02/2017.
 */

public class RegisterClientResponse
{
    @SerializedName("result")
    @Expose
    private boolean result;
    @SerializedName("consumerID")
    @Expose
    private int consumerID;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("SecondsRemaining")
    @Expose
    private String secondsRemaining;
    @SerializedName("existsPhone")
    @Expose
    private boolean existsPhone;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getConsumerID() {
        return consumerID;
    }

    public void setConsumerID(int consumerID) {
        this.consumerID = consumerID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSecondsRemaining() {
        return secondsRemaining;
    }

    public void setSecondsRemaining(String secondsRemaining) {
        this.secondsRemaining = secondsRemaining;
    }

    public boolean isExistsPhone() {
        return existsPhone;
    }

    public void setExistsPhone(boolean existsPhone) {
        this.existsPhone = existsPhone;
    }


}
