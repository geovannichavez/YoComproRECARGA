package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

/**
 * Created by Josué Chávez on 10/11/2017.
 */

public interface ISourvenirsPresenter
{
    void requestSouvenirs();
    void showSouvenirDetailsModal(String title, String description, String count, String url);
}