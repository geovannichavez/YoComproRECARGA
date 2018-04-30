package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.api.Achievement;
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
    ImageView btnBack;
    ImageView btnPlay;
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
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnPlay = (ImageView) findViewById(R.id.btnPlay);
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
    public void showGenericDialog(String title, String content)
    {
        try
        {
            final AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(Achievements.this);
            LayoutInflater inflater = Achievements.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog_generic, null);

            TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvDialogTitle);
            TextView tvDescription = (TextView) dialogView.findViewById(R.id.tvDialogMessage);
            ImageView button = (ImageView) dialogView.findViewById(R.id.btnClose);

            tvTitle.setText(title);
            tvDescription.setText(content);

            dialog = builder.setView(dialogView).create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    dialog.dismiss();
                }
            });

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void loadBackground()
    {
        Picasso.with(this).load(R.drawable.bg_background_4).into(bgTimemachine);
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
