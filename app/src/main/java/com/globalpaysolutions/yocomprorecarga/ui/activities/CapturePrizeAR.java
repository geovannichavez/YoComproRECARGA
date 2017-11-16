package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.ChestData2D;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.LocationPrizeYCRData;
import com.globalpaysolutions.yocomprorecarga.presenters.CapturePrizeARPResenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.views.CapturePrizeView;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseError;
import com.squareup.picasso.Picasso;
import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CapturePrizeAR extends AppCompatActivity implements CapturePrizeView
{
    private static final String TAG = CapturePrizeAR.class.getSimpleName();
    private ArchitectView architectView;

    //Views and layouts
    ProgressDialog progressDialog;
    TextView tvCoinsEarned;
    TextView tvPrizesEarned;
    TextView tvSouvenirCounter;
    TextView tvCoinsCounter;
    //ImageButton btnCoinsCounter;
    ImageButton btnBack;
    ImageButton btnNavigateTimeMachine;
    ImageView ivPrize2D;
    Vibrator mVibrator;
    Toolbar toolbar;
    Toast mToast;

    //Global Variables
    Animation mAnimation;
    Handler mHandler;
    HashMap<String, ChestData2D> mFirbaseObjects;

    //MVP
    CapturePrizeARPResenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_price_ar);
        this.architectView = (ArchitectView) this.findViewById(R.id.architectView);

        tvCoinsEarned = (TextView) findViewById(R.id.tvCoinsEarned);
        tvPrizesEarned = (TextView) findViewById(R.id.tvPrizesEarned);
        tvSouvenirCounter = (TextView) findViewById(R.id.tvSouvenirCounter);
        tvCoinsCounter = (TextView) findViewById(R.id.tvCoinsCounter);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        btnNavigateTimeMachine.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent timemachine = new Intent(CapturePrizeAR.this, Main.class);
                timemachine.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                timemachine.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                timemachine.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                timemachine.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                timemachine.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                timemachine.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(timemachine);
            }
        });

        ivPrize2D = (ImageView) findViewById(R.id.ivPrize2D);
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mAnimation = new AlphaAnimation(1, 0);

        mPresenter = new CapturePrizeARPResenterImpl(this, this, this);
        mPresenter.initialize();
        mHandler = new Handler();
        mFirbaseObjects = new HashMap<>();

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
        createGenericDialog(pMessageModel.getTitle(), pMessageModel.getLine1());
    }

    @Override
    public void showImageDialog(DialogViewModel dialogModel, int resource)
    {
        createImageDialog(dialogModel.getTitle(), dialogModel.getLine1(), resource);
    }

    @Override
    public void showPrizeColectedDialog(DialogViewModel pDialogModel)
    {
        createGenericDialogButton(pDialogModel.getTitle(), pDialogModel.getLine1(), pDialogModel.getAcceptButton(), new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent prizeDetail = new Intent(CapturePrizeAR.this, PrizeDetail.class);
                startActivity(prizeDetail);
            }
        });
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
    public void stopVibrate()
    {
        try
        {
            if (mVibrator != null)
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
        if(mToast != null)
        {
            mToast.cancel();
        }
        mToast = Toast.makeText(CapturePrizeAR.this, pText, Toast.LENGTH_LONG);
        mToast.show();
    }

    @Override
    public void removeBlinkingAnimation()
    {
        ivPrize2D.clearAnimation();
    }

    @Override
    public void onChestTouch(int pAwait)
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
    public void deleteModelAR()
    {
        try
        {
            this.architectView.callJavascript("deleteObjectGeoFn()");
        }
        catch (Exception ex) {  ex.printStackTrace();   }
    }

    @Override
    public void showSouvenirWonDialog(String souvenirName, String souvenirDescription, String url)
    {
        try
        {
            //Creates the builder and inflater of dialog
            AlertDialog souvenirDialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(CapturePrizeAR.this);
            LayoutInflater inflater = CapturePrizeAR.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog_won_sourvenir, null);

            TextView tvSouvenirName = (TextView) dialogView.findViewById(R.id.lblSouvenirName);
            TextView tvSouvenirDesc = (TextView) dialogView.findViewById(R.id.lblSouvenirDescription);
            ImageView imgSouvenir = (ImageView) dialogView.findViewById(R.id.imgSouvenirDialog);

            tvSouvenirName.setText(souvenirName);
            tvSouvenirDesc.setText(souvenirDescription);

            //TODO: Architecture violation - Requests made on Views
            Picasso.with(this).load(url).into(imgSouvenir);

            souvenirDialog = builder.setView(dialogView).create();
            souvenirDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            souvenirDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            souvenirDialog.show();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateIndicators(String pPrizes, String pCoins, String pSouvenirs)
    {
        tvPrizesEarned.setText(pPrizes);
        tvCoinsEarned.setText(pCoins);
        tvSouvenirCounter.setText(pSouvenirs);
    }

    @Override
    public void updatePrizeButton(int pCoins)
    {
        int coinsButton;
        coinsButton = R.drawable.img_coin_counter_twenty;

        tvCoinsCounter.setText(String.valueOf(pCoins));
    }

    @Override
    public void onGoldKeyEntered(String pKey, LatLng pLocation)
    {
        this.architectView.callJavascript("World.createModelGoldAtLocation(" + pLocation.latitude + ", " + pLocation.longitude + ", '" + pKey + "')");
    }

    @Override
    public void onGoldKeyExited(String pKey)
    {
        this.architectView.callJavascript("deleteObjectGeoFn()");
    }

    @Override
    public void onGoldKeyEntered_2D(String pKey, LatLng pLocation)
    {
        ChestData2D data = new ChestData2D();
        data.setLocation(pLocation);
        data.setChestType(Constants.VALUE_CHEST_TYPE_GOLD);

        mFirbaseObjects.clear();
        mFirbaseObjects.put(pKey, data);
        ivPrize2D.setImageResource(R.drawable.img_gold_chest_2d);
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
        ChestData2D data = new ChestData2D();
        data.setLocation(pLocation);
        data.setChestType(Constants.VALUE_CHEST_TYPE_SILVER);

        mFirbaseObjects.clear();
        mFirbaseObjects.put(pKey, data);
        ivPrize2D.setImageResource(R.drawable.img_silver_chest_2d);
    }

    @Override
    public void onSilverKeyExited(String pKey)
    {
        this.architectView.callJavascript("deleteObjectGeoFn()");
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
        ChestData2D data = new ChestData2D();
        data.setLocation(pLocation);
        data.setChestType(Constants.VALUE_CHEST_TYPE_BRONZE);

        mFirbaseObjects.clear();
        mFirbaseObjects.put(pKey, data);
        ivPrize2D.setImageResource(R.drawable.img_bronze_chest_2d);
    }

    @Override
    public void onBronzeKeyExited(String pKey)
    {
        this.architectView.callJavascript("deleteObjectGeoFn()");
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

    public void createImageDialog(String title, String description, int resource)
    {
        try
        {
            final AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(CapturePrizeAR.this);
            LayoutInflater inflater = CapturePrizeAR.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog_generic_image, null);

            TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvDialogTitle);
            TextView tvDescription = (TextView) dialogView.findViewById(R.id.tvDialogMessage);
            ImageView imgSouvenir = (ImageView) dialogView.findViewById(R.id.imgDialogImage);
            ImageButton btnClose = (ImageButton) dialogView.findViewById(R.id.btnClose);

            tvTitle.setText(title);
            tvDescription.setText(description);
            imgSouvenir.setImageResource(resource);

            dialog = builder.setView(dialogView).create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            btnClose.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    dialog.dismiss();
                }
            });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void createGenericDialog(String title, String description)
    {
        try
        {
            final AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(CapturePrizeAR.this);
            LayoutInflater inflater = CapturePrizeAR.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog_generic, null);

            TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvDialogTitle);
            TextView tvDescription = (TextView) dialogView.findViewById(R.id.tvDialogMessage);
            ImageView button = (ImageView) dialogView.findViewById(R.id.btnClose);

            tvTitle.setText(title);
            tvDescription.setText(description);

            dialog = builder.setView(dialogView).create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    dialog.dismiss();
                }
            });

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void createGenericDialogButton(String title, String message, String buttonText, View.OnClickListener listener)
    {
        try
        {
            AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(CapturePrizeAR.this);
            LayoutInflater inflater = CapturePrizeAR.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog_generic_button, null);

            TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvDialogTitle);
            TextView tvDescription = (TextView) dialogView.findViewById(R.id.tvDialogMessage);
            ImageButton btnGenericDialogButton = (ImageButton) findViewById(R.id.btnGenericDialogButton);
            TextView tvButtonContent = (TextView) findViewById(R.id.tvButtonContent);
            btnGenericDialogButton.setOnClickListener(listener);

            tvTitle.setText(title);
            tvDescription.setText(message);


            dialog = builder.setView(dialogView).create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void createNativeDialog(String pTitle, String pMessage, String pButton)
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


    public void redeemPrize(View view)
    {
        mPresenter.redeemPrize();
    }

    public void navigateTimeMachine(View view)
    {
        Intent timeMachine = new Intent(this, Main.class);
        startActivity(timeMachine);
    }




    /*
    *
    *
    *   VIBRATOR AUXILIAR RUNNABLE
    *
    *
    */ Runnable runCoinExchange = new Runnable()
    {
        @Override
        public void run()
        {
            mVibrator.vibrate(Constants.ON_EARNED_COIN_SUCCESSFULLY_VIBRATION_MILLISECONDS);

            try
            {
                Map.Entry<String, ChestData2D> entry = mFirbaseObjects.entrySet().iterator().next();
                String firebaseID = entry.getKey();
                ChestData2D chestData = entry.getValue();

                //Atempt to exchange chest
                mPresenter.exchangeCoinsChest_2D(chestData.getLocation(), firebaseID, chestData.getChestType());
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    };


}
