package com.globalpaysolutions.yocomprorecarga.views;

import android.graphics.Bitmap;

import com.globalpaysolutions.yocomprorecarga.models.api.ListGameStoreResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ListSouvenirsByConsumer;

import java.util.List;

/**
 * Created by Josué Chávez on 12/11/2017.
 */

public interface StoreView
{
    void renderStoreItems(List<ListGameStoreResponse> items);
}
