package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.globalpaysolutions.yocomprorecarga.interactors.CombosListener;

/**
 * Created by Josué Chávez on 16/01/2018.
 */

public interface ICombosInteractor
{
    void retrieveCombos(CombosListener listener);
    void exchangeCombo(CombosListener listener, int comboID);
}
