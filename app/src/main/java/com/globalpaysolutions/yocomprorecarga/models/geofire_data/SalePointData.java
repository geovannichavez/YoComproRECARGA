package com.globalpaysolutions.yocomprorecarga.models.geofire_data;

/**
 * Created by Josué Chávez on 06/03/2017.
 */

public class SalePointData
{
    public String detail;
    public String snipped;
    public String title;

    public SalePointData()
    {

    }

    public String getDetail()
    {
        return detail;
    }

    public String getSnippet()
    {
        return snipped;
    }

    public String getTitle()
    {
        return title;
    }

    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    public void setSnipped(String snipped)
    {
        this.snipped = snipped;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public SalePointData(String pDetail, String pSnippet, String pTitle)
    {
        this.detail = pDetail;
        this.snipped = pSnippet;
        this.title = pTitle;
    }
}
