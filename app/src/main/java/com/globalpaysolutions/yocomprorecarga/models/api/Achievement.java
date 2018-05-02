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
    private int score;
    @SerializedName("Level")
    @Expose
    private int level;
    @SerializedName("ValueNextLevel")
    @Expose
    private int valueNextLevel;
    @SerializedName("Prize")
    @Expose
    private int prize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getValueNextLevel() {
        return valueNextLevel;
    }

    public void setValueNextLevel(int valueNextLevel) {
        this.valueNextLevel = valueNextLevel;
    }

    public int getPrize() {
        return prize;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }


}
