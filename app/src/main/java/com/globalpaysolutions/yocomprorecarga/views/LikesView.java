package com.globalpaysolutions.yocomprorecarga.views;

import android.view.View;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.RewardItem;

import java.util.List;

/**
 * Created by Josué Chávez on 19/3/2018.
 */

public interface LikesView
{
    void initialViews();
    void renderRewards(List<RewardItem> rewards);
    void setClickListeners();
    void showLoadingDialog(String label);
    void hideLoadingDialog();
    void showGenericImageDialog(DialogViewModel dialog, int ic_alert, View.OnClickListener clickListener);


}
