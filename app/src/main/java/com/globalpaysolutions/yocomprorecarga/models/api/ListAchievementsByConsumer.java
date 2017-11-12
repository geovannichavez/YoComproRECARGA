package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 10/11/2017.
 */

public class ListAchievementsByConsumer
{
    @SerializedName("AchievementID")
    @Expose
    private Integer achievementID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("FirstLevel")
    @Expose
    private Integer firstLevel;
    @SerializedName("SecondLevel")
    @Expose
    private Integer secondLevel;
    @SerializedName("ThirdLevel")
    @Expose
    private Integer thirdLevel;
    @SerializedName("Score")
    @Expose
    private Integer score;
    @SerializedName("Level")
    @Expose
    private Integer level;
    @SerializedName("Won")
    @Expose
    private Boolean won;

    public Integer getAchievementID()
    {
        return achievementID;
    }

    public void setAchievementID(Integer achievementID)
    {
        this.achievementID = achievementID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getFirstLevel()
    {
        return firstLevel;
    }

    public void setFirstLevel(Integer firstLevel)
    {
        this.firstLevel = firstLevel;
    }

    public Integer getSecondLevel()
    {
        return secondLevel;
    }

    public void setSecondLevel(Integer secondLevel)
    {
        this.secondLevel = secondLevel;
    }

    public Integer getThirdLevel()
    {
        return thirdLevel;
    }

    public void setThirdLevel(Integer thirdLevel)
    {
        this.thirdLevel = thirdLevel;
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

    public Boolean getWon()
    {
        return won;
    }

    public void setWon(Boolean won)
    {
        this.won = won;
    }
}
