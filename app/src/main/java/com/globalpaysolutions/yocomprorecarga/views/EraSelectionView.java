package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.api.AgesListModel;

import java.util.List;

/**
 * Created by Josué Chávez on 09/11/2017.
 */

public interface EraSelectionView
{
    void initialViews();
    void renderEras(List<AgesListModel> eras);
}
