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
import com.globalpaysolutions.yocomprorecarga.utils.UserData;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Intro extends AppCompatActivity
{
    private static final String TAG = Intro.class.getSimpleName();

    TextView tvDescription;
    ImageView imgTimemachine;
    ImageView imgCounter;
    ImageView imgBag;

    ImageView imgTable;
    ImageView imgBarrell;

    ImageButton btnFinishIntro;
    ConstraintLayout mActivityIntro;


    int mCounter;

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

        mCounter = 0;

        tvDescription = (TextView) findViewById(R.id.tvDescription);
        imgTimemachine = (ImageView) findViewById(R.id.imgTimemachine);
        imgCounter = (ImageView) findViewById(R.id.imgCounter);
        imgBarrell = (ImageView) findViewById(R.id.imgBarrell);
        imgBag = (ImageView) findViewById(R.id.imgBag);
        imgTable = (ImageView) findViewById(R.id.imgTable);
        btnFinishIntro = (ImageButton) findViewById(R.id.btnFinishIntro);
        mActivityIntro = (ConstraintLayout) findViewById(R.id.activity_intro);

        imgTimemachine.setImageResource(R.drawable.img_shadow_timemachine);
        imgCounter.setImageResource(R.drawable.img_shadow_counter);
        imgBarrell.setImageResource(R.drawable.img_shadow_barrell);
        imgBag.setImageResource(R.drawable.img_shadow_bag);
        imgTable.setImageResource(R.drawable.img_shadow_table);

        tvDescription.setText(R.string.label_click_to_intro);

        btnFinishIntro.setEnabled(false);
        btnFinishIntro.setClickable(false);
        btnFinishIntro.setVisibility(View.INVISIBLE);

        imgTable.setVisibility(View.INVISIBLE);
        imgBarrell.setVisibility(View.INVISIBLE);

        mActivityIntro.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mCounter = mCounter + 1;

                switch (mCounter)
                {
                    case 1:
                        imgTimemachine.setImageResource(R.drawable.img_color_timemeachine);
                        tvDescription.setText(getString(R.string.label_intro_intro));
                        break;
                    case 2:
                        tvDescription.setText(R.string.label_intro_2);
                        imgTimemachine.setImageResource(R.drawable.img_shadow_timemachine);
                        imgCounter.setImageResource(R.drawable.img_color_counter);
                        break;
                    case 3:
                        tvDescription.setText(R.string.label_intro_3);
                        imgCounter.setImageResource(R.drawable.img_shadow_counter);
                        imgBag.setImageResource(R.drawable.img_color_bag);
                        break;
                    case 4:
                        tvDescription.setText(R.string.label_intro_4);
                        imgTable.setImageResource(R.drawable.img_color_table);
                        //Changes transition
                        imgTimemachine.setVisibility(View.INVISIBLE);
                        imgCounter.setVisibility(View.INVISIBLE);
                        imgBag.setVisibility(View.INVISIBLE);

                        imgTable.setVisibility(View.VISIBLE);
                        imgBarrell.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        tvDescription.setText(R.string.label_intro_5);
                        imgTable.setImageResource(R.drawable.img_shadow_table);
                        imgBarrell.setImageResource(R.drawable.img_color_barrell);
                        break;
                    case 6 :
                        imgBarrell.setImageResource(R.drawable.img_shadow_barrell);

                        btnFinishIntro.setVisibility(View.VISIBLE);
                        btnFinishIntro.setEnabled(true);
                        btnFinishIntro.setClickable(true);
                        tvDescription.setText(R.string.label_shall_we_begin);

                        btnFinishIntro.setOnClickListener(startClickListener);
                        break;
                    default:
                        Log.i(TAG, "Completed intro");
                        break;
                }
            }

        });
    }


    private View.OnClickListener startClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            ButtonAnimator.getInstance(Intro.this).animateButton(v);
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