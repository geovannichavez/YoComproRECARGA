package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.api.AgesListModel;

import java.util.List;

/**
 * Created by Josué Chávez on 09/11/2017.
 */

public interface EraSelectionView
{
    void showLoadingDialog(String label);
    void hideLoadingDialog();
    void initialViews();
    void renderEras(List<AgesListModel> eras);
    void navigateMap();
    void createImageDialog(String title, String description, int resource);
    void createLockedEraDialog();
    void setSelectedEraName(String eraName);
}
