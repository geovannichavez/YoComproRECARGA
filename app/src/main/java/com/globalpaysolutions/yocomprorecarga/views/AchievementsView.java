package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.api.ListAchievementsByConsumer;

import java.util.List;

/**
 * Created by Josué Chávez on 16/11/2017.
 */

public interface AchievementsView
{
    void showLoadingDialog(String label);
    void hideLoadingDialog();
    void renderAchievements(List<ListAchievementsByConsumer> achieves);
    void showGenericImgDialog(String title, String content, int resource);
}
