package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Josué Chávez on 16/01/2018.
 */

public class Combo
{
    @SerializedName("ComboID")
    @Expose
    private int comboID;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("PrizeDescription")
    @Expose
    private String prizeDescription;
    @SerializedName("Souvenir")
    @Expose
    private List<ComboSouvenir> souvenir = null;

    public int getComboID()
    {
        return comboID;
    }

    public void setComboID(int comboID)
    {
        this.comboID = comboID;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getPrizeDescription()
    {
        return prizeDescription;
    }

    public void setPrizeDescription(String prizeDescription)
    {
        this.prizeDescription = prizeDescription;
    }

    public List<ComboSouvenir> getSouvenir()
    {
        return souvenir;
    }

    public void setSouvenir(List<ComboSouvenir> souvenir)
    {
        this.souvenir = souvenir;
    }

}
