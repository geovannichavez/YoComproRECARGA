package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.AppCompatTextView;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.Leader;
import com.globalpaysolutions.yocomprorecarga.presenters.LeaderboardsPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.LeadersAdapter;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.ImmersiveActivity;
import com.globalpaysolutions.yocomprorecarga.views.LeaderboardsView;
import com.squareup.picasso.Picasso;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Leaderboards extends ImmersiveActivity implements LeaderboardsView
{
    private static final String TAG = Leaderboards.class.getSimpleName();

    //Layouts and Views
    //Toolbar toolbar;
    ListView mLeaderboardListView;
    TextView tvToday;
    TextView tvWeek;
    TextView tvMonth;
    TextView tvGlobal;
    TextView tvLastWinner;
    ProgressDialog progressDialog;
    //ImageView imgButtonsBar;
    ImageButton btnBack;
    ImageView bgTimemachine;

    //Adapters
    LeadersAdapter mLeaderboardAdapter;

    //MVP
    LeaderboardsPresenterImpl mPresenter;

    //Global Variables


    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);

        //Layouts
        mLeaderboardListView = (ListView) findViewById(R.id.lvLeaderboard);
        tvToday = (TextView) findViewById(R.id.btnToday);
        tvWeek = (TextView) findViewById(R.id.btnWeek);
        tvMonth = (TextView) findViewById(R.id.btnMonth);
        tvGlobal = (TextView) findViewById(R.id.btnGlobal);
        //imgButtonsBar = (ImageView) findViewById(R.id.imgButtonsBar);
        tvLastWinner = (TextView) findViewById(R.id.tvLastWinner);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        bgTimemachine = (ImageView) findViewById(R.id.bgTimemachine);
        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.getInstance(Leaderboards.this).animateButton(v);
                Intent main = new Intent(Leaderboards.this, Main.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(main);
                finish();
            }
        });

        //Adapters
        mLeaderboardAdapter = new LeadersAdapter(this, R.layout.custom_leaderboard_listview_item);
        mLeaderboardListView.setAdapter(mLeaderboardAdapter);

        mPresenter = new LeaderboardsPresenterImpl(this, this);
        mPresenter.initialize();
        mPresenter.getLeaderboards(Constants.TODAY, null);

        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
                tvLastWinner, 10, 100,30,TypedValue.COMPLEX_UNIT_SP
        );




    }

    @Override
    public void initializeViews()
    {
        Picasso.with(this).load(R.drawable.bg_background_4).into(bgTimemachine);

        //Sets click listeners
        tvToday.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mPresenter.getLeaderboards(Constants.TODAY, tvToday);
            }
        });

        tvWeek.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mPresenter.getLeaderboards(Constants.WEEK, tvWeek);
            }
        });

        tvMonth.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mPresenter.getLeaderboards(Constants.MONTH, tvMonth);
            }
        });

        tvGlobal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mPresenter.getLeaderboards(Constants.TRIVIA, tvGlobal);
            }
        });
    }

    @Override
    public void renderLeaderboardData(List<Leader> leaders)
    {
        mLeaderboardAdapter.notifyDataSetChanged();

        //Llenado de items en el GridView
        try
        {
            mLeaderboardAdapter.clear();
            for (Leader item : leaders)
            {
                mLeaderboardAdapter.add(item);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void setLastWinner(String data)
    {

        tvLastWinner.setText(data);

//        TextViewCompat.setAutoSizeTextTypeWithDefaults(
//                tvLastWinner,TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM
//        );

    }

    @Override
    public void showLoadingMessage(String message)
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void hideLoadingMessage()
    {
        try
        {
            if (progressDialog != null && progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void showGenericDialog(DialogViewModel dialogViewModel)
    {
        createDialog(dialogViewModel.getTitle(), dialogViewModel.getLine1(), dialogViewModel.getAcceptButton());
    }

    @Override
    public void showGenericToast(String label)
    {
        Toast.makeText(this, label, Toast.LENGTH_LONG).show();
    }

    @Override
    public void changeButtonsBar(String labelValue)
    {
        try
        {
            int resourceImage = 0;
            int resourceImage2 = 0;


            resourceImage = R.drawable.btn_time_on;
            resourceImage2 = R.drawable.btn_time_off;

            switch (labelValue)
            {
                case Constants.TODAY:

                    tvToday.setBackgroundResource(resourceImage);
                    tvWeek.setBackgroundResource(resourceImage2);
                    tvMonth.setBackgroundResource(resourceImage2);
                    tvGlobal.setBackgroundResource(resourceImage2);

                    //imgButtonsBar.setImageResource(resourceImage);
                    break;
                case Constants.WEEK:

                    tvToday.setBackgroundResource(resourceImage2);
                    tvWeek.setBackgroundResource(resourceImage);
                    tvMonth.setBackgroundResource(resourceImage2);
                    tvGlobal.setBackgroundResource(resourceImage2);

                    //imgButtonsBar.setImageResource(resourceImage);
                    break;
                case Constants.MONTH:

                    tvToday.setBackgroundResource(resourceImage2);
                    tvWeek.setBackgroundResource(resourceImage2);
                    tvMonth.setBackgroundResource(resourceImage);
                    tvGlobal.setBackgroundResource(resourceImage2);
                    //imgButtonsBar.setImageResource(resourceImage);
                    break;
                case Constants.TRIVIA:

                    tvToday.setBackgroundResource(resourceImage2);
                    tvWeek.setBackgroundResource(resourceImage2);
                    tvMonth.setBackgroundResource(resourceImage2);
                    tvGlobal.setBackgroundResource(resourceImage);
                    //imgButtonsBar.setImageResource(resourceImage);
                    break;
                default:

                    tvToday.setBackgroundResource(resourceImage);
                    tvWeek.setBackgroundResource(resourceImage2);
                    tvMonth.setBackgroundResource(resourceImage2);
                    tvGlobal.setBackgroundResource(resourceImage2);
                    //imgButtonsBar.setImageResource(resourceImage);

                    break;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void swithTextColor(TextView textView)
    {
        textView.setTextColor(ContextCompat.getColor(this, R.color.dark_recargo_green));
    }


    /*
    *
    *
    * OTHER METHODS
    *
    *
    */
    /*
    *
    *
    *   OTROS METODOS
    *
    */
    public void createDialog(String pTitle, String pMessage, String pButton)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(pTitle);
        alertDialog.setMessage(pMessage);
        alertDialog.setPositiveButton(pButton, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent main = new Intent(Leaderboards.this, Main.class);
            main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(main);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
