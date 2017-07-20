package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 20/07/2017.
 */

public class LastWinner
{
    @SerializedName("Nickname")
    @Expose
    private String nickname;
    @SerializedName("TotalCoins")
    @Expose
    private Integer totalCoins;

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public Integer getTotalCoins()
    {
        return totalCoins;
    }

    public void setTotalCoins(Integer totalCoins)
    {
        this.totalCoins = totalCoins;
    }

}
