package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.NotificationDetailPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.views.NotificationDetailView;

public class NotificationDetail extends AppCompatActivity implements NotificationDetailView
{
    private static final String TAG = NotificationDetail.class.getSimpleName();

    //Layouts and Views
    private TextView tvNotifTitle;
    private TextView tvNotifBody;
    private Button btnAcceptNotif;

    //MVP
    private NotificationDetailPresenterImpl mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);

        String title = getIntent().getStringExtra(Constants.NOTIFICATION_TITLE_EXTRA);
        String body = getIntent().getStringExtra(Constants.NOTIFICATION_BODY_EXTRA);

        tvNotifTitle = (TextView) findViewById(R.id.tvNotifTitle);
        tvNotifBody = (TextView) findViewById(R.id.tvNotifBody);
        btnAcceptNotif = (Button) findViewById(R.id.btnAcceptNotif);

        mPresenter = new NotificationDetailPresenterImpl(this, this, this);
        mPresenter.processNotification(title, body);

        btnAcceptNotif.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mPresenter.finishActivity();
            }
        });

    }

    @Override
    public void renderNotification(String pTitle, String pMessage)
    {
        try
        {
            tvNotifTitle.setText(pTitle);
            tvNotifBody.setText(pMessage);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void finishActivity()
    {
        Log.i(TAG, "User read notification content");
        this.finish();
    }
}
