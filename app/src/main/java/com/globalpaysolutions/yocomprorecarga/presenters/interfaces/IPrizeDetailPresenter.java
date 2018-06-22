package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

import android.os.Bundle;

/**
 * Created by Josué Chávez on 17/07/2017.
 */

public interface IPrizeDetailPresenter
{
    void loadInitialData(Bundle extras);
    void setClickListeners();
    void createSmsPrizeContent(String exchangePin);

    void startCountdownService();
    void stopCountdownService();
}
