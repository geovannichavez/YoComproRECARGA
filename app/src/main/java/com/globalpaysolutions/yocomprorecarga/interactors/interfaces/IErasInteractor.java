package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.globalpaysolutions.yocomprorecarga.interactors.ErasListener;
import com.globalpaysolutions.yocomprorecarga.models.api.AgesListModel;

/**
 * Created by Josué Chávez on 09/11/2017.
 */

public interface IErasInteractor
{
    void eraSelection(int eraID, ErasListener listener);
    void retrieveEras(ErasListener listener);
}
