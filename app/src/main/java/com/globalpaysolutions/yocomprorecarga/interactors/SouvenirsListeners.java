package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.api.ListAchievementsByConsumer;
import com.globalpaysolutions.yocomprorecarga.models.api.ListSouvenirsByConsumer;

import java.util.List;

/**
 * Created by Josué Chávez on 10/11/2017.
 */

public interface SouvenirsListeners
{
    void onSuccess(List<ListSouvenirsByConsumer> souvenirs);
    void onError(int codeStatus, Throwable throwable);
}
