package com.globalpaysolutions.yocomprorecarga.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Josué Chávez on 10/11/2017.
 */

public class SouvenirsResponse
{
    @SerializedName("listSouvenirsByConsumer")
    @Expose
    private List<ListSouvenirsByConsumer> listSouvenirsByConsumer = null;

    public List<ListSouvenirsByConsumer> getListSouvenirsByConsumer() {
        return listSouvenirsByConsumer;
    }

    public void setListSouvenirsByConsumer(List<ListSouvenirsByConsumer> listSouvenirsByConsumer) {
        this.listSouvenirsByConsumer = listSouvenirsByConsumer;
    }
}
