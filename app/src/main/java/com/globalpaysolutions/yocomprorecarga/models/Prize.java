package com.globalpaysolutions.yocomprorecarga.models;

/**
 * Created by Josué Chávez on 20/07/2017.
 */

public class Prize
{
    private String code;
    private String Date;
    private String title;
    private String description;
    private String exchangeMethod;
    private int level;

    public Prize()
    {
    }

    public String getCode()
    {
        return code;
    }

    public String getDate()
    {
        return Date;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public String getExchangeMethod()
    {
        return exchangeMethod;
    }

    public int getLevel()
    {
        return level;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public void setDate(String date)
    {
        Date = date;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setExchangeMethod(String exchangeMethod)
    {
        this.exchangeMethod = exchangeMethod;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public Prize(String code, String date, String title, String description, String exchangeMethod, int level)
    {
        this.code = code;
        this.Date = date;
        this.title = title;
        this.description = description;
        this.exchangeMethod = exchangeMethod;
        this.level = level;
    }
}
