package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.globalpaysolutions.yocomprorecarga.interactors.ErasListener;

/**
 * Created by Josué Chávez on 09/11/2017.
 */

public interface IErasInteractor
{
    void eraSelection(int eraID, ErasListener listener, String destiny);
    void retrieveEras(ErasListener listener);
}
