package com.globalpaysolutions.yocomprorecarga.models;

/**
 * Created by Josué Chávez on 19/07/2017.
 */
public class Leader
{
    private int ranking;
    private String nickname;
    private String recarCoins;

    public Leader()
    {
    }

    public int getRanking()
    {
        return ranking;
    }

    public String getNickname()
    {
        return nickname;
    }

    public String getRecarCoins()
    {
        return recarCoins;
    }

    public void setRanking(int ranking)
    {
        this.ranking = ranking;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public void setRecarCoins(String recarCoins)
    {
        this.recarCoins = recarCoins;
    }

    public Leader(int ranking, String nickname, String recarCoins)
    {
        this.ranking = ranking;
        this.nickname = nickname;
        this.recarCoins = recarCoins;
    }
}
