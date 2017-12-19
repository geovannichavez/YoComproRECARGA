package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

/**
 * Created by Josué Chávez on 17/07/2017.
 */

public interface IPrizeDetailPresenter
{
    void loadInitialData();
    void setClickListeners();
    void createSmsPrizeContent(String exchangePin);
    void setBackground();
}
