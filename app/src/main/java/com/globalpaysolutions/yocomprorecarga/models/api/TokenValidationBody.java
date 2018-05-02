package com.globalpaysolutions.yocomprorecarga.models.api;

/**
 * Created by Josué Chávez on 18/01/2017.
 */

public class TokenValidationBody
{
    private int consumerID;
    private String token;

    public int getConsumerID()
    {
        return consumerID;
    }

    public void setConsumerID(int consumerID)
    {
        this.consumerID = consumerID;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

}
