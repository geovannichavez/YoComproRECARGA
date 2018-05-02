package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

/**
 * Created by Josué Chávez on 20/07/2017.
 */

public interface IPrizesHistoryPresenter
{
    void initialize();
    void retrievePrizes();
    void copyCodeToClipboard(String code);
}
