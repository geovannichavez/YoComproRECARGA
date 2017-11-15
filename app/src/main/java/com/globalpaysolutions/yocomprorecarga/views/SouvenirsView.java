package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.api.ListSouvenirsByConsumer;

import java.util.List;

/**
 * Created by Josué Chávez on 10/11/2017.
 */

public interface SouvenirsView
{
    void renderSouvenirs(List<ListSouvenirsByConsumer> souvenirs);
    void showSouvenirDetails(String title, String description, String count, String url);
}
