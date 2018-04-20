package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 10/11/2017.
 */

public class ListSouvenirsByConsumer
{
    @SerializedName("SouvenirID")
    @Expose
    private int souvenirID;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("AgeID")
    @Expose
    private int ageID;
    @SerializedName("ImgUrl")
    @Expose
    private String imgUrl;
    @SerializedName("Level")
    @Expose
    private int level;
    @SerializedName("SouvenirsOwnedByConsumer")
    @Expose
    private int souvenirsOwnedByConsumer;
    @SerializedName("Unlocked")
    @Expose
    private int unlocked;
    @SerializedName("WorldCupGroup")
    @Expose
    private String worldCupGroup;

    public int getSouvenirID() {
        return souvenirID;
    }

    public void setSouvenirID(int souvenirID) {
        this.souvenirID = souvenirID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAgeID() {
        return ageID;
    }

    public void setAgeID(int ageID) {
        this.ageID = ageID;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSouvenirsOwnedByConsumer() {
        return souvenirsOwnedByConsumer;
    }

    public void setSouvenirsOwnedByConsumer(int souvenirsOwnedByConsumer) {
        this.souvenirsOwnedByConsumer = souvenirsOwnedByConsumer;
    }

    public int getUnlocked() {
        return unlocked;
    }

    public void setUnlocked(int unlocked) {
        this.unlocked = unlocked;
    }

    public String getWorldCupGroup() {
        return worldCupGroup;
    }

    public void setWorldCupGroup(String worldCupGroup) {
        this.worldCupGroup = worldCupGroup;
    }
}
