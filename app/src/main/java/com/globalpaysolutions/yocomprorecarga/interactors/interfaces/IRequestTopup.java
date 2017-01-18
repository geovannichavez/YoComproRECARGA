package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.globalpaysolutions.yocomprorecarga.interactors.RequestTopupListener;

/**
 * Created by Josué Chávez on 16/01/2017.
 */

public interface IRequestTopup
{
    void fetchOperators(RequestTopupListener pListener);
}
