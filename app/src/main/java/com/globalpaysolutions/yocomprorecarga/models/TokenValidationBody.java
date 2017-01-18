package com.globalpaysolutions.yocomprorecarga.models;

/**
 * Created by Josué Chávez on 18/01/2017.
 */

public class TokenValidationBody
{
    private String Msisdn;
    private String Code;

    public String getMsisdn()
    {
        return Msisdn;
    }

    public void setMsisdn(String msisdn)
    {
        this.Msisdn = msisdn;
    }

    public String getCode()
    {
        return Code;
    }

    public void setCode(String code)
    {
        this.Code = code;
    }
}
