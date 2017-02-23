package com.globalpaysolutions.yocomprorecarga.models.api;

/**
 * Created by Josué Chávez on 27/01/2017.
 */

public class RequestTopupReqBody
{
    private Integer consumerId;
    private String targetPhoneNumber;
    private String vendorCode;
    private String operatorID;
    private String amount;

    public Integer getConsumerId()
    {
        return consumerId;
    }

    public void setConsumerId(Integer consumerId)
    {
        this.consumerId = consumerId;
    }

    public String getTargetPhoneNumber()
    {
        return targetPhoneNumber;
    }

    public void setTargetPhoneNumber(String targetPhoneNumber)
    {
        this.targetPhoneNumber = targetPhoneNumber;
    }

    public String getVendorCode()
    {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode)
    {
        this.vendorCode = vendorCode;
    }

    public String getOperatorID()
    {
        return operatorID;
    }

    public void setOperatorID(String operatorID)
    {
        this.operatorID = operatorID;
    }

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }
}

