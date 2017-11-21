package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.NonScrollableListView;
import com.globalpaysolutions.yocomprorecarga.views.LeaderboardsView;

import java.util.List;

public class Leaderboards extends AppCompatActivity implements LeaderboardsView
{
    private static final String TAG = Leaderboards.class.getSimpleName();

    //Layouts and Views
    //Toolbar toolbar;
    //NonScrollableListView mLeaderboardListView;
    ListView mLeaderboardListView;
    TextView tvToday;
    TextView tvWeek;
    TextView tvMonth;
    TextView tvGlobal;
    TextView tvLastWinner;
    ProgressDialog progressDialog;
    ImageView imgButtonsBar;
    ImageButton btnBack;

    //Adapters
    LeadersAdapter mLeaderboardAdapter;

    //MVP
    LeaderboardsPresenterImpl mPresenter;

    //Global Variables


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
        imgButtonsBar = (ImageView) findViewById(R.id.imgButtonsBar);
        tvLastWinner = (TextView) findViewById(R.id.tvLastWinner);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent main = new Intent(Leaderboards.this, Profile.class);
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
    }

    @Override
    public void initializeViews()
    {
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
                mPresenter.getLeaderboards(Constants.OVER_ALL, tvGlobal);
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

            switch (labelValue)
            {
                case Constants.TODAY:
                    resourceImage = R.drawable.btns_leaderboards_today;
                    imgButtonsBar.setImageResource(resourceImage);
                    break;
                case Constants.WEEK:
                    resourceImage = R.drawable.btns_leaderboards_week;
                    imgButtonsBar.setImageResource(resourceImage);
                    break;
                case Constants.MONTH:
                    resourceImage = R.drawable.btns_leaderboards_month;
                    imgButtonsBar.setImageResource(resourceImage);
                    break;
                case Constants.OVER_ALL:
                    resourceImage = R.drawable.btns_leaderboards_global;
                    imgButtonsBar.setImageResource(resourceImage);
                    break;
                default:
                    resourceImage = R.drawable.btns_leaderboards_today;
                    imgButtonsBar.setImageResource(resourceImage);

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
       /* tvToday.setTextColor(ContextCompat.getColor(this, R.color.ActivityWhiteBackground));
        tvWeek.setTextColor(ContextCompat.getColor(this, R.color.ActivityWhiteBackground));
        tvGlobal.setTextColor(ContextCompat.getColor(this, R.color.ActivityWhiteBackground));
        tvMonth.setTextColor(ContextCompat.getColor(this, R.color.ActivityWhiteBackground));*/
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
            Intent main = new Intent(Leaderboards.this, Profile.class);
            main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(main);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
