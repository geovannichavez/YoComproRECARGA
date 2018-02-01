package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import android.graphics.Bitmap;

import com.globalpaysolutions.yocomprorecarga.interactors.ErasListener;
import com.globalpaysolutions.yocomprorecarga.models.api.EraSelectionResponse;

/**
 * Created by Josué Chávez on 09/11/2017.
 */

public interface IErasInteractor
{
    void eraSelection(int eraID, ErasListener listener, String destiny);
    void retrieveEras(ErasListener listener);
    void fetchBitmap(String url, ErasListener listener, String markerName, EraSelectionResponse eraSelection, String destiny);
}
