package com.globalpaysolutions.yocomprorecarga.interactors;

import android.graphics.Bitmap;

import com.globalpaysolutions.yocomprorecarga.models.api.ListGameStoreResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.PurchaseItemResponse;

import java.util.List;

/**
 * Created by Josué Chávez on 12/11/2017.
 */

public interface StoreListener
{
    void onSuccess(List<ListGameStoreResponse> storeItems);
    void onError(int codeStatus, Throwable throwable, String requiredVersion);
    void onImageSuccess(Bitmap bitmap, int itemID);
    void onImageError();
    void onPurchaseSuccess(PurchaseItemResponse response);
    void onPurchaseError(int codeStatus, Throwable throwable, String requiredVersion);
}
