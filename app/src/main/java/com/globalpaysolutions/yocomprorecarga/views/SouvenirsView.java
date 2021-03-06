package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.api.ListSouvenirsByConsumer;

import java.util.List;

/**
 * Created by Josué Chávez on 10/11/2017.
 */

public interface SouvenirsView
{
    void showLoadingDialog(String label);
    void hideLoadingDialog();
    void setInitialViewsState(String eraName);
    void renderSouvenirs(List<ListSouvenirsByConsumer> souvenirs);
    void showSouvenirDetails(String title, String description, String count, String url, int souvID, int counterResource);
    void showNewAchievementDialog(String name, String level, String prize, String score, int resource);
    void navigatePrizeDetails();
    void closeSouvenirDialog();
    void generateImageDialog(DialogViewModel dialog, int resource);
    void showGenericDialog(String title, String content);
}
