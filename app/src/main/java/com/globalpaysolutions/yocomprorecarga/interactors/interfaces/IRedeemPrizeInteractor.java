package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.globalpaysolutions.yocomprorecarga.interactors.RedeemPrizeListener;

/**
 * Created by Josué Chávez on 22/11/2017.
 */

public interface IRedeemPrizeInteractor
{
    void atemptRedeemPrize(String pinCode, String phone, RedeemPrizeListener listener);
}
