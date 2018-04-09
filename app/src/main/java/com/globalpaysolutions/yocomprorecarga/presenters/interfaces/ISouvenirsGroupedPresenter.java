package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

/**
 * Created by Josué Chávez on 4/4/2018.
 */

public interface ISouvenirsGroupedPresenter
{
    void init(String groupName);
    void processGroup(String groupSelected);
    void navigateBackward(String group);
    void navigateForward(String group);
    void showSouvenirDetailsModal(String title, String description, String count, String imgUrl, int souvenirID, int level);
}
