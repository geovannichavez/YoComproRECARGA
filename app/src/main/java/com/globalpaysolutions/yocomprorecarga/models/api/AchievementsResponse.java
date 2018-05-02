package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Josué Chávez on 15/11/2017.
 */

public class AchievementsResponse
{
    @SerializedName("listAchievementsByConsumer")
    @Expose
    private List<ListAchievementsByConsumer> listAchievementsByConsumer = null;

    public List<ListAchievementsByConsumer> getListAchievementsByConsumer()
    {
        return listAchievementsByConsumer;
    }

    public void setListAchievementsByConsumer(List<ListAchievementsByConsumer> listAchievementsByConsumer)
    {
        this.listAchievementsByConsumer = listAchievementsByConsumer;
    }
}
