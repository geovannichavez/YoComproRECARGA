package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

import com.globalpaysolutions.yocomprorecarga.models.api.AgesListModel;

/**
 * Created by Josué Chávez on 09/11/2017.
 */

public interface IEraSelectionPresenter
{
    void initialize();
    void retrieveEras();
    void switchEra(AgesListModel eraSelected);
}
