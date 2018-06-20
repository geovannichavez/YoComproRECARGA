package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Josué Chávez on 13/2/2018.
 */

public class ChallengesResponse
{
    @SerializedName("list")
    @Expose
    private List<Challenge> list = null;

    @SerializedName("LaPulgaWin")
    @Expose
    private int LaPulgaWin;

    @SerializedName("DinhoWin")
    @Expose
    private int DinhoWin;

    @SerializedName("ElPibeWin")
    @Expose
    private int ElPibeWin;

    @SerializedName("ZizouGOWin")
    @Expose
    private int ZizouWin;

    @SerializedName("ElComandanteWin")
    @Expose
    private int ElComandanteWin;



    @SerializedName("ChallengeWin")
    @Expose
    private int challengesWin;

    @SerializedName("ChallengeLose")
    @Expose
    private int challengesLose;

    @SerializedName("ChallengeDraw")
    @Expose
    private int challengesDraw;

    public List<Challenge> getList()
    {
        return list;
    }

    public void setList(List<Challenge> list)
    {
        this.list = list;
    }

    public int getLaPulgaWin()
    {
        return LaPulgaWin;
    }

    public void setLaPulgaWin(int laPulgaWin)
    {
        this.LaPulgaWin = laPulgaWin;
    }

    public int getDinhoWin()
    {
        return DinhoWin;
    }

    public void setDinhoWin(int dinhoWin)
    {
        this.DinhoWin = dinhoWin;
    }

    public int getELPibeWin()
    {
        return ElPibeWin ;
    }

    public void setElPibeWin(int elPibeWin)
    {
        this.ElPibeWin = elPibeWin;
    }

    public int getZizouWin()
    {
        return ZizouWin;
    }

    public void setZizouWin(int zizouWin)
    {
        this.ZizouWin = zizouWin;
    }

    public int getElComandanteWin()
    {
        return ElComandanteWin;
    }

    public void setElComandanteWin(int elComandanteWin)
    {
        this.ElComandanteWin = elComandanteWin;
    }



    public int getChallengesLose()
    {
        return challengesLose;
    }

    public void setChallengesLose(int challengesLose)
    {
        this.challengesLose = challengesLose;
    }


    public int getChallengesWin()
    {
        return challengesWin;
    }

    public void setChallengesWin(int challengesWin)
    {
        this.challengesWin = challengesWin;
    }


    public int getChallengesDraw()
    {
        return challengesDraw;
    }

    public void setChallengesDraw(int challengesDraw)
    {
        this.challengesDraw = challengesDraw;
    }




//"LaPulgaWin": 1,
//        "ElPibeWin": 0,
//        "DinhoWin": 0,
//        "ZizouGOWin": 0,
//        "ElComandanteWin": 0,
//        "ChallengeWin": 49,
//        "ChallengeLose": 62,
//        "ChallengeDraw": 218,

}
