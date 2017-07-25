package com.globalpaysolutions.yocomprorecarga.ui.activities;

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
    Toolbar toolbar;
    TextView tvName;
    TextView tvNickname;
    CircleImageView ivProfilePicture;

    //MVP
    ProfilePresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbarProfile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvName = (TextView) findViewById(R.id.tvName);
        tvNickname = (TextView) findViewById(R.id.tvNickname);
        ivProfilePicture = (CircleImageView) findViewById(R.id.ivProfilePicture);

        mPresenter = new ProfilePresenterImpl(this, this, this);
        mPresenter.loadInitialData();
    }

    @Override
    public void loadViewsState(String fullName, String nickname, String photoUrl)
    {
        tvName.setText(fullName);
        tvNickname.setText(nickname);
        if(TextUtils.isEmpty(photoUrl))
        {
            ivProfilePicture.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.img_profile_picture));
        }
        else
        {
            Picasso.with(this).load(photoUrl).into(ivProfilePicture);
        }
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

    public void viewTutorial(View view)
    {
        mPresenter.viewTutorial();
    }
}
