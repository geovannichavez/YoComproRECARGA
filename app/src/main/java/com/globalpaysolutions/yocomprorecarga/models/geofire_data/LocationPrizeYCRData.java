package com.globalpaysolutions.yocomprorecarga.models.geofire_data;

/**
 * Created by Josué Chávez on 28/03/2017.
 */

public class LocationPrizeYCRData
{
    public String brand;
    public String coins;
    public String detail;

    public String getBrand()
    {
        return brand;
    }

    public String getCoins()
    {
        return coins;
    }

    public String getDetail()
    {
        return detail;
    }

    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    public void setCoins(String coins)
    {
        this.coins = coins;
    }

    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    public LocationPrizeYCRData(String pBrand, String pCoins, String pDetail)
    {
        this.brand = pBrand;
        this.coins = pCoins;
        this.detail = pCoins;
    }
}
