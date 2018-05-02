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
    private Boolean result;
    @SerializedName("consumerID")
    @Expose
    private Integer consumerID;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("SecondsRemaining")
    @Expose
    private String secondsRemaining;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Integer getConsumerID() {
        return consumerID;
    }

    public void setConsumerID(Integer consumerID) {
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

}
