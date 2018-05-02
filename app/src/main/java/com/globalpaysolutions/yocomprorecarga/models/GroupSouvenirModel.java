package com.globalpaysolutions.yocomprorecarga.models;

/**
 * Created by Josué Chávez on 4/4/2018.
 */

public class GroupSouvenirModel
{
    private String group;
    private int id;

    public GroupSouvenirModel(String group, int id)
    {
        this.group = group;
        this.id = id;
    }

    public String getGroup()
    {
        return group;
    }

    public int getId()
    {
        return id;
    }

    public void setGroup(String group)
    {
        this.group = group;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
