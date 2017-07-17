package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.LocationPrizeYCRData;
import com.globalpaysolutions.yocomprorecarga.presenters.CapturePrizeARPResenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.CustomClickListener;
import com.globalpaysolutions.yocomprorecarga.views.CapturePrizeView;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseError;
import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;

import java.io.IOException;

public class CapturePrizeAR extends AppCompatActivity implements CapturePrizeView
{
    private static final String TAG = CapturePrizeAR.class.getSimpleName();
    private ArchitectView architectView;

    //Views and layouts
    ProgressDialog progressDialog;
    TextView tvCoinsEarned;
    TextView tvPrizesEarned;
    ImageButton btnCoinsCounter;
    ImageButton ivPrize2D;
    Vibrator mVibrator;
    Toolbar toolbar;

    //Global Variables
    Animation mAnimation;
    Handler mHandler;

    //MVP
    CapturePrizeARPResenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_price_ar);
        this.architectView = (ArchitectView) this.findViewById(R.id.architectView);

        toolbar = (Toolbar) findViewById(R.id.arToolbar);
        setSupportActionBar(toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        tvCoinsEarned = (TextView) findViewById(R.id.tvCoinsEarned);
        tvPrizesEarned = (TextView) findViewById(R.id.tvPrizesEarned);
        btnCoinsCounter = (ImageButton) findViewById(R.id.btnCoinCounter);
        ivPrize2D = (ImageButton) findViewById(R.id.ivPrize2D);
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mAnimation = new AlphaAnimation(1, 0);

        mPresenter = new CapturePrizeARPResenterImpl(this, this, this);
        mPresenter.initialize();
        mHandler = new Handler();

        final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();
        config.setLicenseKey(Constants.WIKITUDE_LICENSE_KEY);
        this.architectView.onCreate(config);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        this.architectView.onPostCreate();
        try
        {
            this.architectView.load("index.html");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUserLocation(double pLatitude, double pLongitude, double pAccuracy)
    {
        LatLng location = new LatLng(pLatitude, pLongitude);
        this.mPresenter.updatePrizePntCriteria(location);
        this.architectView.setLocation(pLatitude, pLongitude, pAccuracy);
    }

    @Override
    public void locationManagerConnected(double pLatitude, double pLongitude, double pAccuracy)
    {
        LatLng location = new LatLng(pLatitude, pLongitude);
        this.mPresenter.prizePointsQuery(location);
        architectView.setLocation(pLatitude, pLongitude, pAccuracy);
    }

    @Override
    public void onPOIClick()
    {
        try
        {
            this.architectView.registerUrlListener(new ArchitectView.ArchitectUrlListener()
            {
                @Override
                public boolean urlWasInvoked(String s)
                {
                    Log.i(TAG, s);
                    mPresenter.exchangeCoinsChest(s);
                    return false;
                }
            });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    @Override
    public void onCoinLongClick()
    {
        ivPrize2D.setOnTouchListener(new View.OnTouchListener()
        {
            long then;
            long longClickDuration = Constants.REQUIRED_TIME_TOUCH_MILLISECONDS;

            @Override
            public boolean onTouch(View view, MotionEvent event)
            {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    then = System.currentTimeMillis();

                    //Stops vibrating and removes animation
                    mPresenter.handleCoinTouch();
                }
                else if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    if ((System.currentTimeMillis() - then) > longClickDuration)
                    {
                        //Long click behavior here
                        return false;
                    }
                    else
                    {
                        //Short click behavior here
                        mPresenter.handleCoinExchangeKeyUp();
                        return true;
                    }
                }

                return false;
            }
        });
    }

    @Override
    public void hideArchViewLoadingMessage()
    {
        this.architectView.callJavascript("World.worldLoaded()");
    }

    @Override
    public void showGenericDialog(DialogViewModel pMessageModel)
    {
        createDialog(pMessageModel.getTitle(), pMessageModel.getLine1(), pMessageModel.getAcceptButton());
    }

    @Override
    public void showPrizeColectedDialog(DialogViewModel pDialogModel)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CapturePrizeAR.this);
        alertDialog.setTitle(pDialogModel.getTitle());
        alertDialog.setMessage(pDialogModel.getLine1());
        alertDialog.setPositiveButton(pDialogModel.getAcceptButton(), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                Intent prizeDetail = new Intent(CapturePrizeAR.this, PrizeDetail.class);
                startActivity(prizeDetail);
            }
        });
        alertDialog.show();
    }

    @Override
    public void showIncompatibleDeviceDialog(DialogViewModel pMessageModel)
    {
        String message = String.format("%1$s %2$s.", pMessageModel.getLine1(), pMessageModel.getLine2());

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CapturePrizeAR.this);
        alertDialog.setTitle(pMessageModel.getTitle());
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(pMessageModel.getAcceptButton(), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                Intent returnHome = new Intent(CapturePrizeAR.this, Home.class);
                returnHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                returnHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                returnHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                returnHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                returnHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                returnHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(returnHome);
            }
        });
        alertDialog.show();
    }

    @Override
    public void navigatePrizeDetail()
    {
        Intent prizeDetail = new Intent(this, PrizeDetail.class);
        startActivity(prizeDetail);
    }

    @Override
    public void showLoadingDialog(String pLabel)
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(pLabel);
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
    public void obtainUserProgress()
    {
        mPresenter.retrieveUserTracking();
    }

    @Override
    public void switchRecarcoinVisible(boolean pVisible)
    {
        int visible = (pVisible) ? View.VISIBLE : View.GONE;
        ivPrize2D.setVisibility(visible);
    }

    @Override
    public void blinkRecarcoin()
    {
        mAnimation.setDuration(300);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.REVERSE);
        ivPrize2D.startAnimation(mAnimation);
    }

    @Override
    public void makeVibrate(int pVibrationMs, int pSleepMs)
    {
        //Resets vibrator if it was already vibrating
        mVibrator.cancel();

        long[] pattern = {0, pVibrationMs, pSleepMs};

        mVibrator.vibrate(pattern, 0);
    }

    @Override
    public void stopVibrate()
    {
        try
        {
            if(mVibrator != null)
                mVibrator.cancel();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void showToast(String pText)
    {
        Toast.makeText(CapturePrizeAR.this, pText, Toast.LENGTH_LONG).show();
    }

    @Override
    public void removeBlinkingAnimation()
    {
        ivPrize2D.clearAnimation();
    }

    @Override
    public void onCoinTouch(int pAwait)
    {
        mVibrator.cancel();
        removeBlinkingAnimation();
        mHandler.postDelayed(runCoinExchange, pAwait);
    }

    @Override
    public void removeRunnableCallback()
    {
        mHandler.removeCallbacks(runCoinExchange);
    }

    @Override
    public void updateIndicators(String pPrizes, String pCoins)
    {
        tvPrizesEarned.setText(pPrizes);
        tvCoinsEarned.setText(pCoins);
    }

    @Override
    public void updatePrizeButton(int pCoins)
    {
        int coinsButton;

        switch (pCoins)
        {
            case 0:
                coinsButton = R.drawable.img_coin_counter_zero;
                break;
            case 1:
                coinsButton = R.drawable.img_coin_counter_one;
                break;
            case 2:
                coinsButton = R.drawable.img_coin_counter_two;
                break;
            case 3:
                coinsButton = R.drawable.img_coin_counter_three;
                break;
            case 4:
                coinsButton = R.drawable.img_coin_counter_four;
                break;
            case 5:
                coinsButton = R.drawable.img_coin_counter_five;
                break;
            case 6:
                coinsButton = R.drawable.img_coin_counter_six;
                break;
            case 7:
                coinsButton = R.drawable.img_coin_counter_seven;
                break;
            case 8:
                coinsButton = R.drawable.img_coin_counter_eight;
                break;
            case 9:
                coinsButton = R.drawable.img_coin_counter_nine;
                break;
            case 10:
                coinsButton = R.drawable.img_coin_counter_ten;
                break;
            case 11:
                coinsButton = R.drawable.img_coin_counter_eleven;
                break;
            case 12:
                coinsButton = R.drawable.img_coin_counter_twelve;
                break;
            case 13:
                coinsButton = R.drawable.img_coin_counter_thirteen;
                break;
            case 14:
                coinsButton = R.drawable.img_coin_counter_fourteen;
                break;
            case 15:
                coinsButton = R.drawable.img_coin_counter_fifteen;
                break;
            case 16:
                coinsButton = R.drawable.img_coin_counter_sixteen;
                break;
            case 17:
                coinsButton = R.drawable.img_coin_counter_seventeen;
                break;
            case 18:
                coinsButton = R.drawable.img_coin_counter_eighteen;
                break;
            case 19:
                coinsButton = R.drawable.img_coin_counter_nineteen;
                break;
            case 20:
                coinsButton = R.drawable.img_coin_counter_twenty;
                break;
            default:
                coinsButton = R.drawable.img_coin_counter_zero;
                break;
        }

        btnCoinsCounter.setImageResource(coinsButton);
    }

    @Override
    public void onGoldKeyEntered(String pKey, LatLng pLocation)
    {
        this.architectView.callJavascript("World.createModelGoldAtLocation(" + pLocation.latitude + ", " + pLocation.longitude + ", '" + pKey + "')");
    }

    @Override
    public void onGoldKeyExited(String pKey)
    {

    }

    @Override
    public void onGoldKeyEntered_2D(String pKey, LatLng pLocation)
    {
        //ivPrize2D.setImageResource(R.drawable.img_recarstop_2d_gold);
    }

    @Override
    public void onGoldPointDataChange(String pKey, LocationPrizeYCRData pGoldPointData)
    {

    }

    @Override
    public void onGoldPointCancelled(DatabaseError pDatabaseError)
    {

    }

    @Override
    public void onSilverKeyEntered(String pKey, LatLng pLocation)
    {
        this.architectView.callJavascript("World.createModelSilverAtLocation(" + pLocation.latitude + ", " + pLocation.longitude + ", '" + pKey + "')");
    }

    @Override
    public void onSilverKeyEntered_2D(String pKey, LatLng pLocation)
    {
        //ivPrize2D.setImageResource(R.drawable.img_recarstop_2d_silver);
    }

    @Override
    public void onSilverKeyExited(String pKey)
    {

    }

    @Override
    public void onSilverPointDataChange(String pKey, LocationPrizeYCRData pGoldPointData)
    {

    }

    @Override
    public void onSilverPointCancelled(DatabaseError pDatabaseError)
    {

    }

    @Override
    public void onBronzeKeyEntered(String pKey, LatLng pLocation)
    {
        this.architectView.callJavascript("World.createModelBronzeAtLocation(" + pLocation.latitude + ", " + pLocation.longitude + ", '" + pKey + "')");
    }

    @Override
    public void onBronzeKeyEntered_2D(String pKey, LatLng pLocation)
    {
        //ivPrize2D.setImageResource(R.drawable.img_recarstop_2d_bronze);
    }

    @Override
    public void onBronzeKeyExited(String pKey)
    {

    }

    @Override
    public void onBronzePointDataChange(String pKey, LocationPrizeYCRData pGoldPointData)
    {

    }

    @Override
    public void onBronzePointCancelled(DatabaseError pDatabaseError)
    {

    }


    /*
    *
    *   ACTIVITY LIFECYCLES
    *
    */
    @Override
    protected void onResume()
    {
        super.onResume();
        architectView.onResume();
        mPresenter.resume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        architectView.onPause();

        try
        {
            if(mVibrator != null)
                mVibrator.cancel();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        try
        {
            if(mVibrator != null)
                mVibrator.cancel();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        architectView.onDestroy();

        try
        {
            if(mVibrator != null)
                mVibrator.cancel();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }



    /*
    *
    *
    * OTROS METODOS
    *
    */

    public void createDialog(String pTitle, String pMessage, String pButton)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CapturePrizeAR.this);
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

    public int getStatusBarHeight()
    {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void redeemPrize(View view)
    {
        mPresenter.redeemPrize();
        /*Intent prize = new Intent(this, PrizeDetail.class);
        startActivity(prize);*/
    }


    /*
    *
    *
    *   VIBRATOR AUXILIAR RUNNABLE
    *
    *
    */
    Runnable runCoinExchange = new Runnable()
    {
        @Override
        public void run()
        {
            mVibrator.vibrate(Constants.ON_EARNED_COIN_SUCCESSFULLY_VIBRATION_MILLISECONDS);
            mPresenter._navigateToPrize();
        }
    };


}
