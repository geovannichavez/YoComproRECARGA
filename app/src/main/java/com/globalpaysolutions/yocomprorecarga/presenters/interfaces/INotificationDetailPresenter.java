package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

import org.json.JSONObject;

/**
 * Created by Josué Chávez on 23/03/2017.
 */

public interface INotificationDetailPresenter
{
    void processNotification(String pTitle, String pBody);
    void finishActivity();
}
