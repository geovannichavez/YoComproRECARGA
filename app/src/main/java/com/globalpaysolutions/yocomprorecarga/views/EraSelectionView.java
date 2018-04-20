package com.globalpaysolutions.yocomprorecarga.views;

import android.view.View;

import com.globalpaysolutions.yocomprorecarga.models.api.AgesListModel;

import java.util.List;

/**
 * Created by Josué Chávez on 09/11/2017.
 */

public interface EraSelectionView
{
    void setBackground();
    void showLoadingDialog(String label);
    void hideLoadingDialog();
    void initialViews();
    void renderEras(List<AgesListModel> eras);
    void navigateMap();
    void forwardToStore();
    void forwardToChallenges();
    void forwardWorldcupCountrySelection();
    void createImageDialog(String title, String description, int resource, View.OnClickListener clickListener);
    void createLockedEraDialog();
    void setSelectedEraName(String eraName);
    void showGenericDialog(String title, String message);
    void setTravelingAnim();
    void hideTravlingAnim();
    void navigateMain();

}
