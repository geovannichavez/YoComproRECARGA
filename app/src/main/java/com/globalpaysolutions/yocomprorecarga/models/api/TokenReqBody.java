package com.globalpaysolutions.yocomprorecarga.models.api;

/**
 * Created by Josué Chávez on 18/01/2017.
 */

public class TokenReqBody
{
    private String Msisdn;
    private String CountryId;

    public String getMsisdn()
    {
        return Msisdn;
    }

    public void setMsisdn(String msisdn)
    {
        this.Msisdn = msisdn;
    }

    public String getCountryId()
    {
        return CountryId;
    }

    public void setCountryId(String countryId)
    {
        this.CountryId = countryId;
    }
}
