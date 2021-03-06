package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Josué Chávez on 15/11/2017.
 */

public class EraSelectionResponse
{
    @SerializedName("AgeID")
    @Expose
    private int ageID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("IconImage")
    @Expose
    private String iconImage;
    @SerializedName("MarkerG")
    @Expose
    private String markerG;
    @SerializedName("MarkerS")
    @Expose
    private String markerS;
    @SerializedName("MarkerB")
    @Expose
    private String markerB;
    @SerializedName("MarkerW")
    @Expose
    private String markerW;
    @SerializedName("WildcardMain")
    @Expose
    private String wildcardMain;
    @SerializedName("WildcardWin")
    @Expose
    private String wildcardWin;
    @SerializedName("WildcardLose")
    @Expose
    private String wildcardLose;
    @SerializedName("PrizeImage")
    @Expose
    private String prizeImage;
    @SerializedName("ChallengeRock")
    @Expose
    private String challengeRock;
    @SerializedName("ChallengePaper")
    @Expose
    private String challengePaper;
    @SerializedName("ChallengeScissors")
    @Expose
    private String challengeScissors;
    @SerializedName("CountryID")
    @Expose
    private int countryID;
    @SerializedName("CountryName")
    @Expose
    private String countryName;
    @SerializedName("UrlImg")
    @Expose
    private String urlImg;
    @SerializedName("UrlImgMarker")
    @Expose
    private String urlImgMarker;
    @SerializedName("FolderName")
    @Expose
    private String folderName;

    public int getAgeID()
    {
        return ageID;
    }

    public void setAgeID(int ageID)
    {
        this.ageID = ageID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getIconImage()
    {
        return iconImage;
    }

    public void setIconImage(String iconImage)
    {
        this.iconImage = iconImage;
    }

    public String getMarkerG()
    {
        return markerG;
    }

    public void setMarkerG(String markerG)
    {
        this.markerG = markerG;
    }

    public String getMarkerS()
    {
        return markerS;
    }

    public void setMarkerS(String markerS)
    {
        this.markerS = markerS;
    }

    public String getMarkerB()
    {
        return markerB;
    }

    public void setMarkerB(String markerB)
    {
        this.markerB = markerB;
    }

    public String getMarkerW()
    {
        return markerW;
    }

    public void setMarkerW(String markerW)
    {
        this.markerW = markerW;
    }

    public String getWildcardMain()
    {
        return wildcardMain;
    }

    public void setWildcardMain(String wildcardMain)
    {
        this.wildcardMain = wildcardMain;
    }

    public String getWildcardWin()
    {
        return wildcardWin;
    }

    public void setWildcardWin(String wildcardWin)
    {
        this.wildcardWin = wildcardWin;
    }

    public String getWildcardLose()
    {
        return wildcardLose;
    }

    public void setWildcardLose(String wildcardLose)
    {
        this.wildcardLose = wildcardLose;
    }

    public String getPrizeImage()
    {
        return prizeImage;
    }

    public void setPrizeImage(String prizeImage)
    {
        this.prizeImage = prizeImage;
    }

    public String getChallengeRock()
    {
        return challengeRock;
    }

    public void setChallengeRock(String challengeRock)
    {
        this.challengeRock = challengeRock;
    }

    public String getChallengePaper()
    {
        return challengePaper;
    }

    public void setChallengePaper(String challengePaper)
    {
        this.challengePaper = challengePaper;
    }

    public String getChallengeScissors()
    {
        return challengeScissors;
    }

    public void setChallengeScissors(String challengeScissors)
    {
        this.challengeScissors = challengeScissors;
    }

    public int getCountryID()
    {
        return countryID;
    }

    public void setCountryID(int countryID)
    {
        this.countryID = countryID;
    }

    public String getCountryName()
    {
        return countryName;
    }

    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }

    public String getUrlImg()
    {
        return urlImg;
    }

    public void setUrlImg(String urlImg)
    {
        this.urlImg = urlImg;
    }

    public String getUrlImgMarker()
    {
        return urlImgMarker;
    }

    public void setUrlImgMarker(String urlImgMarker)
    {
        this.urlImgMarker = urlImgMarker;
    }

    public String getFolderName()
    {
        return folderName;
    }

    public void setFolderName(String folderName)
    {
        this.folderName = folderName;
    }

}
