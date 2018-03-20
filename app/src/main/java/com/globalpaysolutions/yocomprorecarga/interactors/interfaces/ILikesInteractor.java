package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.globalpaysolutions.yocomprorecarga.interactors.LikesListener;
import com.globalpaysolutions.yocomprorecarga.presenters.LikesPresenterImpl;

/**
 * Created by Josué Chávez on 19/3/2018.
 */

public interface ILikesInteractor
{
    void likeFanpage(LikesListener listener);
    void requestReward(int option, LikesListener listener);
}
