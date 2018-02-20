package com.globalpaysolutions.yocomprorecarga.models.geofire_data;

/**
 * Created by Josué Chávez on 7/2/2018.
 */

public class PlayerPointData
{
    private String Nickname;

    public PlayerPointData()
    {

    }

    public PlayerPointData(String Nickname)
    {
        this.Nickname = Nickname;
    }

    public String getNickname()
    {
        return Nickname;
    }
}
