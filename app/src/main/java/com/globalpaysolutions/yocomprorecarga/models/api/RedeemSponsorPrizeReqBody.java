package com.globalpaysolutions.yocomprorecarga.models.api;

public class RedeemSponsorPrizeReqBody
{

    /*@SerializedName("Longitude")
    @Expose*/
    private Double longitude;
    /*@SerializedName("Latitude")
    @Expose*/
    private Double latitude;
    /*@SerializedName("ChestType")
    @Expose*/
    private int prizeType;
    /*@SerializedName("ChestType")
    @Expose*/
    private String brand;

    public Double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(Double longitude)
    {
        this.longitude = longitude;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(Double latitude)
    {
        this.latitude = latitude;
    }

    public int getPrizeType()
    {
        return prizeType;
    }

    public void setPrizeType(int prizeType)
    {
        this.prizeType = prizeType;
    }

    public String getBrand()
    {
        return brand;
    }

    public void setBrand(String brand)
    {
        this.brand = brand;
    }

}
