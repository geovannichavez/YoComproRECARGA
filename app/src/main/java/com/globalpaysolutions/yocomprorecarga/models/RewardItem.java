package com.globalpaysolutions.yocomprorecarga.models;

import com.globalpaysolutions.yocomprorecarga.utils.Constants;

/**
 * Created by Josué Chávez on 19/3/2018.
 */

public class RewardItem
{
    private Constants.FacebookActions action;
    private String description;
    private String reward;
    private String urlImage;

    public RewardItem()
    {
    }

    public Constants.FacebookActions getAction()
    {
        return action;
    }

    public void setAction(Constants.FacebookActions action)
    {
        this.action = action;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getReward()
    {
        return reward;
    }

    public void setReward(String reward)
    {
        this.reward = reward;
    }

    public String getUrlImage()
    {
        return urlImage;
    }

    public void setUrlImage(String urlImage)
    {
        this.urlImage = urlImage;
    }
}
