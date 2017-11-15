package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

/**
 * Created by Josué Chávez on 12/11/2017.
 */

public interface IStorePresenter
{
    void retrieveStoreItems();
    void purchaseitem(int itemID);
    void navigateNext();
}
