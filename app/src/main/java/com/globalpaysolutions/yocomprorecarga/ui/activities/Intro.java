package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.Intent;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;

public class Intro extends AppCompatActivity
{

    TextView tvDescription;
    ImageView imgTimemachine;
    ImageView imgCounter;
    ImageView imgBag;

    ImageView imgTable;
    ImageView imgBarrell;

    ImageButton btnFinishIntro;


    int mCounter;


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

        imgTable.setEnabled(false);
        imgTable.setClickable(false);

        imgBarrell.setEnabled(false);
        imgBag.setClickable(false);

        imgTimemachine.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mCounter = mCounter + 1;

                imgTimemachine.setImageResource(R.drawable.img_color_timemeachine);
                tvDescription.setText(getString(R.string.label_intro_intro));

                if (mCounter > 1)
                {
                    tvDescription.setText(R.string.label_intro_2);
                    imgTimemachine.setImageResource(R.drawable.img_shadow_timemachine);
                    imgCounter.setImageResource(R.drawable.img_color_counter);
                    imgTimemachine.setEnabled(false);
                    imgTimemachine.setClickable(false);
                }
            }
        });

        imgCounter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tvDescription.setText(R.string.label_intro_3);
                imgCounter.setImageResource(R.drawable.img_shadow_counter);
                imgBag.setImageResource(R.drawable.img_color_bag);
                imgCounter.setEnabled(false);
                imgCounter.setClickable(false);
            }
        });

        imgBag.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tvDescription.setText(R.string.label_intro_4);
                imgTable.setImageResource(R.drawable.img_color_table);
                //Changes transition
                imgTimemachine.setVisibility(View.INVISIBLE);
                imgCounter.setVisibility(View.INVISIBLE);
                imgBag.setVisibility(View.INVISIBLE);

                imgTable.setEnabled(true);
                imgBarrell.setEnabled(true);
                imgTable.setVisibility(View.VISIBLE);
                imgBarrell.setVisibility(View.VISIBLE);
                imgBag.setEnabled(false);
                imgBag.setClickable(false);
            }
        });

        imgTable.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tvDescription.setText(R.string.label_intro_5);
                imgTable.setImageResource(R.drawable.img_shadow_table);
                imgBarrell.setImageResource(R.drawable.img_color_barrell);
                imgTable.setEnabled(false);
                imgTable.setClickable(false);
            }
        });

        imgBarrell.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                imgBarrell.setImageResource(R.drawable.img_shadow_barrell);
                imgBarrell.setEnabled(false);
                imgBarrell.setClickable(false);

                btnFinishIntro.setVisibility(View.VISIBLE);
                btnFinishIntro.setEnabled(true);
                btnFinishIntro.setClickable(true);
                tvDescription.setText(R.string.label_shall_we_begin);
            }
        });

        btnFinishIntro.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(UserData.getInstance(Intro.this).getHasSeenIntroValue())
                {
                    Intent intent = new Intent(Intro.this, Main.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }
                else
                {
                    Intent inputToken = new Intent(Intro.this, AcceptTerms.class);
                    inputToken.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    inputToken.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    inputToken.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    inputToken.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    inputToken.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    inputToken.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);

                    UserData.getInstance(Intro.this).hasSeenIntro(true);

                    startActivity(inputToken);
                }

            }
        });

    }
}