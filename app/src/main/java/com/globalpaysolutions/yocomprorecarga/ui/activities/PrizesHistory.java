package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.Prize;
import com.globalpaysolutions.yocomprorecarga.presenters.PrizeDetailPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.presenters.PrizesHistoryPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.PrizesAdapter;
import com.globalpaysolutions.yocomprorecarga.utils.NonScrollableListView;
import com.globalpaysolutions.yocomprorecarga.views.PrizesHistoryView;

import java.util.List;

public class PrizesHistory extends AppCompatActivity implements PrizesHistoryView
{
    private static final String TAG = PrizesHistory.class.getSimpleName();

    //Layouts and views
    Toolbar toolbar;
    ListView mHistoryListview;
    ProgressDialog progressDialog;

    //Adapters
    PrizesAdapter mPrizesAdapter;

    //MVP
    PrizesHistoryPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prizes_history);
        toolbar = (Toolbar) findViewById(R.id.toolbarPrizesHistory);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Presenter
        mPresenter = new PrizesHistoryPresenterImpl(this, this);

        //Layouts
        mHistoryListview = (ListView) findViewById(R.id.lvHistory);

        //Initialize views
        mPresenter.initialize();

        //Adapter
        mPrizesAdapter = new PrizesAdapter(this, R.layout.custom_prizes_history_listview_item);
        mHistoryListview.setAdapter(mPrizesAdapter);


        mPresenter.retrievePrizes();

    }

    @Override
    public void initializeViews()
    {
        mHistoryListview.setOnTouchListener(new ListView.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                int action = event.getAction();
                switch (action)
                {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });
    }

    @Override
    public void renderPrizes(List<Prize> prizes)
    {
        mPrizesAdapter.notifyDataSetChanged();

        //Llenado de items en el GridView
        try
        {
            mPrizesAdapter.clear();
            for (Prize item : prizes)
            {
                mPrizesAdapter.add(item);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void showGenericToast(String content)
    {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoadingDialog(String label)
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(label);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void hideLoadingDialog()
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
    public void showGenericDialog(DialogViewModel dialogModel)
    {
        createDialog(dialogModel.getTitle(), dialogModel.getLine1(), dialogModel.getAcceptButton());
    }

    /*
    *
    *
    * OTHER METHODS
    *
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
}
