package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

/**
 * Created by Josué Chávez on 20/07/2017.
 */

public interface IPrizesHistoryPresenter
{
    void initialize();
    void retrievePrizes(int menuOption, int categoryID);
    void copyCodeToClipboard(String code);
    void markPrizeRedeemed(int winPrizeID, boolean redeemed);
}
