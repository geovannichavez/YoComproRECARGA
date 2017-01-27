package com.globalpaysolutions.yocomprorecarga.models;

/**
 * Created by Josué Chávez on 27/01/2017.
 */

public class RequestTopupReqBody
{
    private String OperatorId;

    private String Amount;

    private String CountryId;

    private String VendorCode;

    private String MSISDN;

    public String getOperatorId ()
    {
        return OperatorId;
    }

    public void setOperatorId (String OperatorId)
    {
        this.OperatorId = OperatorId;
    }

    public String getAmount ()
    {
        return Amount;
    }

    public void setAmount (String Amount)
    {
        this.Amount = Amount;
    }

    public String getCountryId ()
    {
        return CountryId;
    }

    public void setCountryId (String CountryId)
    {
        this.CountryId = CountryId;
    }

    public String getVendorCode ()
    {
        return VendorCode;
    }

    public void setVendorCode (String VendorCode)
    {
        this.VendorCode = VendorCode;
    }

    public String getMSISDN ()
    {
        return MSISDN;
    }

    public void setMSISDN (String MSISDN)
    {
        this.MSISDN = MSISDN;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [OperatorId = "+OperatorId+", Amount = "+Amount+", CountryId = "+CountryId+", VendorCode = "+VendorCode+", MSISDN = "+MSISDN+"]";
    }
}

