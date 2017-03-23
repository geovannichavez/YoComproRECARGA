package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.INotificationDetailPresenter;
import com.globalpaysolutions.yocomprorecarga.views.NotificationDetailView;

import org.json.JSONObject;

/**
 * Created by Josué Chávez on 23/03/2017.
 */

public class NotificationDetailPresenterImpl implements INotificationDetailPresenter
{
    private Context mContext;
    private NotificationDetailView mView;

    public NotificationDetailPresenterImpl(Context pContext, AppCompatActivity pActivity, NotificationDetailView pView)
    {
        this.mContext = pContext;
        this.mView = pView;
    }

    @Override
    public void processNotification(String pTitle, String pBody)
    {
        mView.renderNotification(pTitle, pBody);
    }

    @Override
    public void finishActivity()
    {
        mView.finishActivity();
    }
}
