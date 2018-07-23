package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.api.Combo;
import com.globalpaysolutions.yocomprorecarga.presenters.CombosPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.CombosAdapter;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.views.CombosView;
import com.squareup.picasso.Picasso;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Combos extends AppCompatActivity implements CombosView
{
    private static final String TAG = Combos.class.getSimpleName();

    //MVP
    private CombosPresenterImpl mPresenter;

    //Views and Layouts
    private ImageView bgTimemachine;
    private ImageView btnBack;
    private ImageView btnStore;
    private ProgressDialog mProgressDialog;
    private AlertDialog mConfirmDialog;

    //Global variables
    private RecyclerView mRecyclerView;
    private CombosAdapter mAdapter;
    private Constants.CombosNavigationStack mBackStack;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combos);

        mBackStack = (Constants.CombosNavigationStack) getIntent().getSerializableExtra(Constants.BUNDLE_COMBOS_BACK_STACK);

        bgTimemachine = (ImageView) findViewById(R.id.bgTimemachine);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnStore = (ImageView) findViewById(R.id.btnStore);

        mRecyclerView = (RecyclerView) findViewById(R.id.lvCombos);

        mPresenter = new CombosPresenterImpl(this, this, this);
        mPresenter.initialize();
        mPresenter.retrieveCombos();

    }

    @Override
    public void initialViews()
    {
        try
        {
            Picasso.with(this).load(R.drawable.bg_background_4).into(bgTimemachine);

            //Back button click
            btnBack.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    navigateBack();
                    finish();
                }
            });

            //Navigate to Store
            btnStore.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent main = new Intent(Combos.this, Store.class);
                    main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(main);
                    finish();
                }
            });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void navigateBack()
    {
        try
        {
            Intent back = new Intent(Combos.this, Souvenirs.class);

            if(mBackStack != null)
            {
                if(mBackStack.equals(Constants.CombosNavigationStack.SOUVENIRs_GROUPED))
                    back = new Intent(Combos.this, SouvenirsGroups.class);
            }

            back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(back);
            finish();
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error going back: " + ex.getMessage());
        }
    }

    @Override
    public void showLoadingDialog(String label)
    {
        try
        {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(label);
            mProgressDialog.show();
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void hideLoadingDialog()
    {
        try
        {
            if (mProgressDialog != null && mProgressDialog.isShowing())
            {
                mProgressDialog.dismiss();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void renderCombos(final List<Combo> combos)
    {
        try
        {
            mAdapter = new CombosAdapter(this, combos, mPresenter);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.getRecycledViewPool().setMaxRecycledViews(1,0); //To prevent recycle
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            mRecyclerView.setAdapter(mAdapter);

            mAdapter.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void showGenericImageDialog(DialogViewModel dialog, int resource)
    {
        createImageDialog(dialog.getTitle(), dialog.getLine1(), resource);
    }

    @Override
    public void showExchangeConfirmDialog(DialogViewModel dialogContent, int resource, View.OnClickListener onClickListener)
    {
        try
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog_confirm_image, null);

            TextView tvTitle = (TextView) dialogView.findViewById(R.id.lblTitle);
            TextView tvDescription = (TextView) dialogView.findViewById(R.id.lblContent);
            TextView tvButtonContent = (TextView) dialogView.findViewById(R.id.tvButtonContent);
            ImageView imgDialogImage = (ImageView) dialogView.findViewById(R.id.imgDialogImage);
            ImageButton btnClose = (ImageButton) dialogView.findViewById(R.id.btnClose);
            ImageButton btnGenericDialogButton = (ImageButton) dialogView.findViewById(R.id.btnGenericDialogButton);

            btnGenericDialogButton.setOnClickListener(onClickListener);

            tvTitle.setText(dialogContent.getTitle());
            tvDescription.setText(dialogContent.getLine1());
            tvButtonContent.setText(dialogContent.getAcceptButton());
            Picasso.with(this).load(resource).into(imgDialogImage);

            mConfirmDialog = builder.setView(dialogView).create();
            mConfirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mConfirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mConfirmDialog.show();

            btnClose.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    mConfirmDialog.dismiss();
                }
            });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void hideConfirmDialog()
    {
        try
        {
            if (mConfirmDialog != null && mConfirmDialog.isShowing())
            {
                mConfirmDialog.cancel();
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error trying to dismiss 'mConfirmDialog': " + ex.getMessage());
        }
    }

    @Override
    public void navigatePrizeDetails()
    {
        Intent intent = new Intent(this, PrizeDetail.class);
        intent.putExtra(Constants.BUNDLE_PRIZEDET_BACKS, Constants.PrizeDetailsNavigationStack.COMBOS);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            navigateBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void createImageDialog(String title, String description, int resource)
    {
        try
        {
            final AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog_generic_image, null);

            TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvDialogTitle);
            TextView tvDescription = (TextView) dialogView.findViewById(R.id.tvDialogMessage);
            ImageView imgSouvenir = (ImageView) dialogView.findViewById(R.id.imgDialogImage);
            ImageButton btnClose = (ImageButton) dialogView.findViewById(R.id.btnClose);

            tvTitle.setText(title);
            tvDescription.setText(description);
            Picasso.with(this).load(resource).into(imgSouvenir);

            dialog = builder.setView(dialogView).create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            btnClose.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ButtonAnimator.getInstance(Combos.this).animateButton(v);
                    dialog.dismiss();
                }
            });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
