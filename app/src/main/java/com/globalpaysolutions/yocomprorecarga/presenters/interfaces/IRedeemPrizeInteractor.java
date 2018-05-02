package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

/**
 * Created by Josué Chávez on 22/11/2017.
 */

public interface IRedeemPrizeInteractor
{
    void attemptActivatePrize(String phone, String pin);
    void presetPinCode();
}
