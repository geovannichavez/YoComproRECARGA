package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

/**
 * Created by Josué Chávez on 08/03/2017.
 */

public interface IPermissions
{
    void checkPermission();
    void onPermissionsResult(int pRequestCode, String pPermissions[], int[] pGrantResults);
}
