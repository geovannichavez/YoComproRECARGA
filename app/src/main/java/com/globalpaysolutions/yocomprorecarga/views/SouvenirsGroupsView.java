package com.globalpaysolutions.yocomprorecarga.views;

import android.view.View;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.GroupSouvenirModel;

import java.util.List;

/**
 * Created by Josué Chávez on 3/4/2018.
 */

public interface SouvenirsGroupsView
{
    void initializeViews(String progress, int stars);
    void renderGroups(List<GroupSouvenirModel> groups);
    void updateSouvsProgress(String progress, int stars);
    void showLoadingDialog(String text);
    void hideLoadingDialog();
    void showGenericDialog(DialogViewModel content, View.OnClickListener clickListener);
}
