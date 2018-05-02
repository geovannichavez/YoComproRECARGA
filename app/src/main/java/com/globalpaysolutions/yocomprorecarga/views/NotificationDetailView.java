package com.globalpaysolutions.yocomprorecarga.views;

/**
 * Created by Josué Chávez on 23/03/2017.
 */

public interface NotificationDetailView
{
    void renderNotification(String pTitle, String pMessage);
    void finishActivity();

    void loadBackground();
}
