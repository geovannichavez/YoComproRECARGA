package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.api.ListAchievementsByConsumer;
import com.globalpaysolutions.yocomprorecarga.presenters.AchievementsPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.AchievementsAdapter;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.ImmersiveActivity;
import com.globalpaysolutions.yocomprorecarga.views.AchievementsView;
import com.squareup.picasso.Picasso;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Achievements extends ImmersiveActivity implements AchievementsView
{
    private static final String TAG = Achievements.class.getSimpleName();

    //MVP
    private AchievementsPresenterImpl mPresenter;

    //Global Variables
    AchievementsAdapter mAdapter;
    ImageButton btnBack;
    ImageButton btnPlay;
    ProgressDialog mProgressDialog;
    ImageView bgTimemachine;

    //Layouts and Views
    ListView lvAchievements;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        lvAchievements = (ListView) findViewById(R.id.lvAchievements);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        bgTimemachine = (ImageView) findViewById(R.id.bgWhiteTimemachine);

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.getInstance(Achievements.this).animateButton(v);
                Intent main = new Intent(Achievements.this, Profile.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(main);
                finish();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.getInstance(Achievements.this).animateButton(v);
                Intent intent = new Intent(Achievements.this, PointsMap.class);
                startActivity(intent);
                finish();
            }
        });

        mPresenter = new AchievementsPresenterImpl(this, this, this);
        mAdapter = new AchievementsAdapter(this, R.layout.custom_achievement_listview_item);

        mPresenter.loadBackground();

        lvAchievements.setAdapter(mAdapter);

        mPresenter.retrieveAchievements();
    }

    @Override
    public void showLoadingDialog(String label)
    {
        try
        {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(label);
            mProgressDialog.show();
            //mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void hideLoadingDialog()
    {
        try
        {
            if (mProgressDialog != null && mProgressDialog.isShowing())
            {
                mProgressDialog.dismiss();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void renderAchievements(List<ListAchievementsByConsumer> achieves)
    {
        try
        {
            mAdapter.notifyDataSetChanged();
            mAdapter.clear();

            for (ListAchievementsByConsumer item : achieves)
            {
                mAdapter.add(item);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void showGenericImgDialog(String title, String content, int resource)
    {

    }

    @Override
    public void loadBackground()
    {
        Picasso.with(this).load(R.drawable.bg_time_machine).into(bgTimemachine);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent main = new Intent(Achievements.this, Profile.class);
            main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(main);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
