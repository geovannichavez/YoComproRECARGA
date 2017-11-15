package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 12/11/2017.
 */

public class Achievement
{
    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("Score")
    @Expose
    private Integer score;

    @SerializedName("Level")
    @Expose
    private Integer level;

    @SerializedName("ValueNextLevel")
    @Expose
    private Integer valueNextLevel;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getScore()
    {
        return score;
    }

    public void setScore(Integer score)
    {
        this.score = score;
    }

    public Integer getLevel()
    {
        return level;
    }

    public void setLevel(Integer level)
    {
        this.level = level;
    }

    public Integer getValueNextLevel()
    {
        return valueNextLevel;
    }

    public void setValueNextLevel(Integer valueNextLevel)
    {
        this.valueNextLevel = valueNextLevel;
    }
}
