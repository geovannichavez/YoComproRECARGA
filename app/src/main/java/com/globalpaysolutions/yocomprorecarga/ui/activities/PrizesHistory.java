package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.Prize;
import com.globalpaysolutions.yocomprorecarga.presenters.PrizeDetailPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.presenters.PrizesHistoryPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.PrizesAdapter;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.ImmersiveActivity;
import com.globalpaysolutions.yocomprorecarga.utils.NonScrollableListView;
import com.globalpaysolutions.yocomprorecarga.views.PrizesHistoryView;

import org.w3c.dom.Text;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PrizesHistory extends ImmersiveActivity implements PrizesHistoryView
{
    private static final String TAG = PrizesHistory.class.getSimpleName();

    //Layouts and views
    Toolbar toolbar;
    ListView mHistoryListview;
    ProgressDialog progressDialog;
    ImageButton btnActivatePrize;
    ImageButton btnBack;
    TextView tvNoPrizesYetTitle;
    TextView tvNoPrizesYetContent;

    //Adapters
    PrizesAdapter mPrizesAdapter;

    //MVP
    PrizesHistoryPresenterImpl mPresenter;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prizes_history);

        //Layouts
        mHistoryListview = (ListView) findViewById(R.id.lvHistory);
        btnActivatePrize = (ImageButton) findViewById(R.id.btnActivatePrize);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        tvNoPrizesYetTitle = (TextView) findViewById(R.id.tvNoPrizesYetTitle);
        tvNoPrizesYetContent = (TextView) findViewById(R.id.tvNoPrizesYetContent);


        btnActivatePrize.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.getInstance(PrizesHistory.this).animateButton(v);
                Intent intent = new Intent(PrizesHistory.this, RedeemPrize.class);
                startActivity(intent);
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.getInstance(PrizesHistory.this).animateButton(v);
                Intent main = new Intent(PrizesHistory.this, Profile.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(main);
                finish();
            }
        });

        //Presenter
        mPresenter = new PrizesHistoryPresenterImpl(this, this);

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
       try
       {
           mHistoryListview.setVisibility(View.VISIBLE);
           tvNoPrizesYetTitle.setVisibility(View.INVISIBLE);
           tvNoPrizesYetContent.setVisibility(View.INVISIBLE);

           mHistoryListview.setOnItemClickListener(new AdapterView.OnItemClickListener()
           {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id)
               {
                   Prize currentItem = ((Prize) parent.getItemAtPosition(position));
                   Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                   smsIntent.setType("vnd.android-dir/mms-sms");
                   smsIntent.putExtra("address", Constants.SMS_NUMBER_PRIZE_EXCHANGE);
                   smsIntent.putExtra("sms_body",currentItem.getCode());
                   startActivity(smsIntent);
               }
           });
       }
       catch (Exception ex)
       {
           Log.e(TAG, ex.getMessage());
           Crashlytics.logException(ex);
       }
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

    @Override
    public void showNoPrizesText()
    {
        tvNoPrizesYetTitle.setVisibility(View.VISIBLE);
        tvNoPrizesYetContent.setVisibility(View.VISIBLE);
        mHistoryListview.setVisibility(View.INVISIBLE);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent main = new Intent(PrizesHistory.this, Profile.class);
            main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(main);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
