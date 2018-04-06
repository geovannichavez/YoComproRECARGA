package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.api.ListSouvenirsByConsumer;

import java.util.List;

/**
 * Created by Josué Chávez on 4/4/2018.
 */

public interface SouvenirsGroupedView
{
    void init(String groupName);
    void renderSouvs(List<ListSouvenirsByConsumer> souvsList, String groupUpdated);
    void updateCurrentGroup(String letter);
    void showSouvenirDetails(String title, String description, String count, String url, int souvID, int counterResource);
}
