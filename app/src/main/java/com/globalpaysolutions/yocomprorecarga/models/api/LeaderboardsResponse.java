package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Josué Chávez on 19/07/2017.
 */

public class LeaderboardsResponse
{
    @SerializedName("Leaderboards")
    @Expose
    private List<Leaderboard> leaderboards = null;

    @SerializedName("LastWinner")
    @Expose
    private LastWinner lastWinner;

    public List<Leaderboard> getLeaderboards()
    {
        return leaderboards;
    }

    public void setLeaderboards(List<Leaderboard> leaderboards)
    {
        this.leaderboards = leaderboards;
    }

    public LastWinner getLastWinner()
    {
        return lastWinner;
    }

    public void setLastWinner(LastWinner lastWinner)
    {
        this.lastWinner = lastWinner;
    }
}
