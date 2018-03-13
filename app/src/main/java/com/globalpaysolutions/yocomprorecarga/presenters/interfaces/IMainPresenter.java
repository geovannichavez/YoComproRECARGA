package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

/**
 * Created by Josué Chávez on 06/11/2017.
 */

public interface IMainPresenter
{
    void setInitialViews();
    void checkUserDataCompleted();
    void checkFunctionalityLimitedShown();
    void showcaseSeen(boolean seen);
    void downloadMarkers();
    void checkPermissions();
    void onPermissionsResult(int requestCode, String permissions[], int[] grantResults);
    void retrievePendings();
    //void mustReselectEra(String destiny);
}
