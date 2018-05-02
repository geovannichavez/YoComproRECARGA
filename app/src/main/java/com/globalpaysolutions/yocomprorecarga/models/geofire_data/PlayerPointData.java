package com.globalpaysolutions.yocomprorecarga.models.geofire_data;

/**
 * Created by Josué Chávez on 7/2/2018.
 */

public class PlayerPointData
{
    private String Nickname;
    private String MarkerUrl;

    public PlayerPointData()
    {

    }

    public PlayerPointData(String Nickname, String MarkerUrl)
    {
        this.Nickname = Nickname;
        this.MarkerUrl = MarkerUrl;
    }

    public String getNickname()
    {
        return Nickname;
    }

    public void setNickname(String nickname)
    {
        this.Nickname = nickname;
    }

    public void setMarkerUrl(String markerUrl)
    {
        this.MarkerUrl = markerUrl;
    }

    public String getMarkerUrl()
    {
        return MarkerUrl;
    }
}
