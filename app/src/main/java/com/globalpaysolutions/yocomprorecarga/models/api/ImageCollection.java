package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Josué Chávez on 15/11/2017.
 */

public class ImageCollection
{
    @SerializedName("ImgUrl")
    @Expose
    private String imgUrl;
    @SerializedName("Type")
    @Expose
    private Object type;
    @SerializedName("Sequence")
    @Expose
    private Integer sequence;

    public String getImgUrl()
    {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }

    public Object getType()
    {
        return type;
    }

    public void setType(Object type)
    {
        this.type = type;
    }

    public Integer getSequence()
    {
        return sequence;
    }

    public void setSequence(Integer sequence)
    {
        this.sequence = sequence;
    }

}
