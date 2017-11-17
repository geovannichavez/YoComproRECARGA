package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.presenters.ProfilePresenterImpl;
import com.globalpaysolutions.yocomprorecarga.views.ProfileView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity implements ProfileView
{
    private static final String TAG = Profile.class.getSimpleName();

    //Views and layouts
    TextView tvNickname;
    CircleImageView ivProfilePicture;
    ImageButton btnBack;

    //MVP
    ProfilePresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvNickname = (TextView) findViewById(R.id.lblNickname);
        ivProfilePicture = (CircleImageView) findViewById(R.id.ivProfilePicture);
        btnBack = (ImageButton) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        mPresenter = new ProfilePresenterImpl(this, this, this);
        mPresenter.loadInitialData();
    }

    @Override
    public void loadViewsState(String fullName, String nickname, String photoUrl)
    {
        tvNickname.setText(nickname);
        if(TextUtils.isEmpty(photoUrl))
        {
            ivProfilePicture.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.img_profile_picture));
        }
        else
        {
            Picasso.with(this).load(photoUrl).into(ivProfilePicture);
        }

        try
        {
            View decorView = getWindow().getDecorView();
            int ui = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(ui);

            //Hide actionbar
            ActionBar actionBar = getActionBar();
            actionBar.hide();
        }
        catch (Exception ex) {ex.printStackTrace();}
    }

    @Override
    public void generateToast(String text)
    {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showGenericDialog(DialogViewModel model)
    {

    }

    @Override
    public void launchChromeView(String url)
    {
        try
        {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            builder.setToolbarColor(ContextCompat.getColor(this, R.color.ActivityWhiteBackground));
            customTabsIntent.launchUrl(this, Uri.parse(url));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.e(TAG, "Something went wrong launching Chrome View");
        }
    }

    public void navigateLeaderboards(View view)
    {
        Intent leaderboards = new Intent(this, Leaderboards.class);
        startActivity(leaderboards);
    }

    public void navigatePrizesHistory(View view)
    {
        Intent history = new Intent(this, PrizesHistory.class);
        startActivity(history);
    }

    public void navigateSouvenirs(View view)
    {
        Intent souvenirs = new Intent(this, Souvenirs.class);
        startActivity(souvenirs);
    }

    public void navigateAchievements(View view)
    {
        Intent achievements = new Intent(this, Achievements.class);
        startActivity(achievements);
    }

    public void viewTutorial(View view)
    {
        mPresenter.viewTutorial();
    }
}
