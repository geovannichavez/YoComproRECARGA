package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

/**
 * Created by Josué Chávez on 16/01/2017.
 */

public interface IAcceptTerms
{
    void acceptTerms();
    void setFirstTimeSettings();
    void grantDevicePermissions();
    void checkDeviceComponents();
    void viewTerms();
    void generateWebDialog();
}
