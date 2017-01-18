package com.globalpaysolutions.yocomprorecarga.models;

/**
 * Created by Josué Chávez on 18/01/2017.
 */

public class TokenReqBody
{
    private String msisdn;
    private String countryId;

    public String getMsisdn()
    {
        return msisdn;
    }

    public void setMsisdn(String msisdn)
    {
        this.msisdn = msisdn;
    }

    public String getCountryId()
    {
        return countryId;
    }

    public void setCountryId(String countryId)
    {
        this.countryId = countryId;
    }
}
