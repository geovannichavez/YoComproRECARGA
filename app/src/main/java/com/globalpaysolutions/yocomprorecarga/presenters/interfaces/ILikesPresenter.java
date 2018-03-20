package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

import com.globalpaysolutions.yocomprorecarga.utils.Constants;

/**
 * Created by Josué Chávez on 19/3/2018.
 */

public interface ILikesPresenter
{
    void initialize();
    void createItems();
    void requestReward();
    void saveLastShareSelection(Constants.FacebookActions action);
}
