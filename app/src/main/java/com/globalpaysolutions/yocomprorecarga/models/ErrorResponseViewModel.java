package com.globalpaysolutions.yocomprorecarga.models;

/**
 * Created by Josué Chávez on 13/01/2017.
 */

public class ErrorResponseViewModel
{
    private String Title;
    private String Line1;
    private String Line2;
    private String Line3;
    private String Line4;
    private String AcceptButton;
    private String CanelButton;
    private String CustomButton;

    public String getTitle()
    {
        return Title;
    }

    public String getLine1()
    {
        return Line1;
    }

    public String getLine2()
    {
        return Line2;
    }

    public String getLine3()
    {
        return Line3;
    }

    public String getLine4()
    {
        return Line4;
    }

    public String getAcceptButton()
    {
        return AcceptButton;
    }

    public String getCanelButton()
    {
        return CanelButton;
    }

    public String getCustomButton()
    {
        return CustomButton;
    }

    public void setTitle(String title)
    {
        Title = title;
    }

    public void setLine1(String line1)
    {
        Line1 = line1;
    }

    public void setLine2(String line2)
    {
        Line2 = line2;
    }

    public void setLine3(String line3)
    {
        Line3 = line3;
    }

    public void setLine4(String line4)
    {
        Line4 = line4;
    }

    public void setAcceptButton(String acceptButton)
    {
        AcceptButton = acceptButton;
    }

    public void setCanelButton(String canelButton)
    {
        CanelButton = canelButton;
    }

    public void setCustomButton(String customButton)
    {
        CustomButton = customButton;
    }

    public ErrorResponseViewModel()
    {

    }

    public ErrorResponseViewModel(String pTitle, String pLine1, String pLine2, String pLine3, String pLine4, String pAcceptBtn, String pCanelBtn, String pCustomBn)
    {
        this.setTitle(pTitle);
        this.setLine1(pLine1);
        this.setLine2(pLine2);
        this.setLine3(pLine3);
        this.setLine4(pLine4);
        this.setAcceptButton(pAcceptBtn);
        this.setCanelButton(pCanelBtn);
        this.setCustomButton(pCustomBn);
    }
}
