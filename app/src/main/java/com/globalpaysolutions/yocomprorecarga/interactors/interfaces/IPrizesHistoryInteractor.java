package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

/**
 * Created by Josué Chávez on 20/07/2017.
 */

public interface IPrizesHistoryInteractor
{
    void retrievePrizesHistory(int menuOption, int categoryID);
    void setRedeemedPrize(int winPrizeID, boolean redeemed);
}
