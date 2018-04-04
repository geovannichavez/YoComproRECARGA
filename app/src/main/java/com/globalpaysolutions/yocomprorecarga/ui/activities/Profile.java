package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.media.Image;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.presenters.ProfilePresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.ImmersiveActivity;
import com.globalpaysolutions.yocomprorecarga.views.ProfileView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Profile extends ImmersiveActivity implements ProfileView
{
    private static final String TAG = Profile.class.getSimpleName();

    //Views and layouts
    TextView tvNickname;
    CircleImageView ivProfilePicture;
    ImageButton btnBack;
    ImageView bgTimemachine;

    //MVP
    ProfilePresenterImpl mPresenter;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvNickname = (TextView) findViewById(R.id.lblNickname);
        ivProfilePicture = (CircleImageView) findViewById(R.id.ivProfilePicture);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        bgTimemachine = (ImageView) findViewById(R.id.bgTimemachine);

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                animateButton(v);
                Intent main = new Intent(Profile.this, Main.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(main);
                finish();
            }
        });

        mPresenter = new ProfilePresenterImpl(this, this, this);
        mPresenter.loadBackground();
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

    @Override
    public void setBackground()
    {
        Picasso.with(this).load(R.drawable.bg_time_machine).into(bgTimemachine);
    }

    public void navigateLeaderboards(View view)
    {
        animateButton(view);
        Intent leaderboards = new Intent(this, Leaderboards.class);
        leaderboards.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(leaderboards);
        finish();
    }

    public void navigateChallenges(View view)
    {
        animateButton(view);
        Intent challenges = new Intent(this, Challenges.class);
        challenges.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(challenges);
        finish();
    }

    public void navigatePrizesHistory(View view)
    {
        animateButton(view);
        Intent history = new Intent(this, PrizesHistory.class);
        history.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(history);
        finish();
    }

    public void navigateSouvenirs(View view)
    {
        animateButton(view);
        //Intent souvenirs = new Intent(this, Souvenirs.class);
        Intent souvenirs = new Intent(this, SouvenirsGroups.class);
        souvenirs.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(souvenirs);
        finish();
    }

    public void navigateAchievements(View view)
    {
        animateButton(view);
        Intent achievements = new Intent(this, Achievements.class);
        achievements.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(achievements);
        finish();
    }

    public void viewTutorial(View view)
    {
        mPresenter.viewTutorial();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent main = new Intent(Profile.this, Main.class);
            main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(main);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void animateButton(View view)
    {
        try
        {
            ImageButton button = (ImageButton) findViewById(view.getId());

            Drawable drawableNormal = button.getDrawable();
            Drawable drawablePressed = button.getDrawable().getConstantState().newDrawable();
            drawablePressed.mutate();
            drawablePressed.setColorFilter(Color.argb(50, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);

            StateListDrawable listDrawable = new StateListDrawable();
            listDrawable.addState(new int[] {android.R.attr.state_pressed}, drawablePressed);
            listDrawable.addState(new int[] {}, drawableNormal);
            button.setImageDrawable(listDrawable);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
