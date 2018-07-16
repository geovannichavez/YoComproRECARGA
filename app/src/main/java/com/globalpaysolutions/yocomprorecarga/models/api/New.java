package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class New
{
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("RegDate")
    @Expose
    private String regDate;
    @SerializedName("SponsorLogo")
    @Expose
    private String sponsorLogo;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getRegDate()
    {
        return regDate;
    }

    public void setRegDate(String regDate)
    {
        this.regDate = regDate;
    }

    public String getSponsorLogo()
    {
        return sponsorLogo;
    }

    public void setSponsorLogo(String sponsorLogo)
    {
        this.sponsorLogo = sponsorLogo;
    }
}
