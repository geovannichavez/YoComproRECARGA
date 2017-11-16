package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.globalpaysolutions.yocomprorecarga.interactors.StoreListener;

/**
 * Created by Josué Chávez on 12/11/2017.
 */

public interface IStoreInteractor
{
    void retrieveStoreItems(StoreListener listener);
    void purchaseStoreItem(StoreListener listener, int itemID);
    void downloadItemImage(String url, int itemID, StoreListener listener);
}
