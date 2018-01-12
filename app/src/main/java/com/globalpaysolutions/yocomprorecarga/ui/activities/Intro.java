package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.ImmersiveActivity;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Intro extends ImmersiveActivity
{
    private static final String TAG = Intro.class.getSimpleName();

    private int mCounter;
    ConstraintLayout mActivityIntro;
    ImageView bgIntro;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.color_intro_background));

        mActivityIntro = (ConstraintLayout) findViewById(R.id.activity_intro);
        bgIntro = (ImageView) findViewById(R.id.bgIntro);
        mCounter = 0;

        //Sets initial image on ImageView
        Picasso.with(this).load(R.drawable.img_intro_1).into(bgIntro);

        mActivityIntro.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                switch (mCounter)
                {
                    case 1:
                        Picasso.with(Intro.this).load(R.drawable.img_intro_1).into(bgIntro);
                        break;
                    case 2:
                        Picasso.with(Intro.this).load(R.drawable.img_intro_2).into(bgIntro);
                        break;
                    case 3:
                        Picasso.with(Intro.this).load(R.drawable.img_intro_3).into(bgIntro);
                        break;
                    case 4:
                        Picasso.with(Intro.this).load(R.drawable.img_intro_4).into(bgIntro);
                        break;
                    case 5:
                        Picasso.with(Intro.this).load(R.drawable.img_intro_5).into(bgIntro);
                        break;
                    case 6:
                        if(UserData.getInstance(Intro.this).getHasSeenIntroValue())
                        {
                            Intent intent = new Intent(Intro.this, Main.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Intent inputToken = new Intent(Intro.this, AcceptTerms.class);
                            inputToken.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            inputToken.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            inputToken.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            inputToken.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            inputToken.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            inputToken.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            UserData.getInstance(Intro.this).hasSeenIntro(true);

                            startActivity(inputToken);
                            finish();
                        }
                        break;
                }
                mCounter++;
            }
        });


    }


    private View.OnClickListener startClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {

        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
           if(UserData.getInstance(Intro.this).getHasSeenIntroValue())
           {
               Intent main = new Intent(this, Main.class);
               main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
               startActivity(main);
               finish();
           }
           else
           {
               finish();
           }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}