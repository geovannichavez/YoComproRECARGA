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
    private int achievementID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Score")
    @Expose
    private int score;
    @SerializedName("Level")
    @Expose
    private int level;
    @SerializedName("Won")
    @Expose
    private boolean won;
    @SerializedName("NextPrize")
    @Expose
    private int nextPrize;
    @SerializedName("NextLevel")
    @Expose
    private int nextLevel;
    @SerializedName("Description")
    @Expose
    private String description;

    public int getAchievementID() {
        return achievementID;
    }

    public void setAchievementID(int achievementID) {
        this.achievementID = achievementID;
    }

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

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public int getNextPrize() {
        return nextPrize;
    }

    public void setNextPrize(int nextPrize) {
        this.nextPrize = nextPrize;
    }

    public int getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(int nextLevel) {
        this.nextLevel = nextLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
