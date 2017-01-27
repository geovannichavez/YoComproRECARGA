package com.globalpaysolutions.yocomprorecarga.models;

/**
 * Created by Josué Chávez on 26/01/2017.
 */

public class Amount
{
    private String Description;

    private String Amount;

    private String OperatorName;

    private String Code;

    public String getDescription ()
    {
        return Description;
    }

    public void setDescription (String Description)
    {
        this.Description = Description;
    }

    public String getAmount ()
    {
        return Amount;
    }

    public void setAmount (String Amount)
    {
        this.Amount = Amount;
    }

    public String getOperatorName ()
    {
        return OperatorName;
    }

    public void setOperatorName (String OperatorName)
    {
        this.OperatorName = OperatorName;
    }

    public String getCode ()
    {
        return Code;
    }

    public void setCode (String Code)
    {
        this.Code = Code;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Description = "+Description+", Amount = "+Amount+", OperatorName = "+OperatorName+", Code = "+Code+"]";
    }
}
