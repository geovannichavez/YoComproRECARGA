package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.GroupSouvenirModel;
import com.globalpaysolutions.yocomprorecarga.presenters.SouvenirsGroupPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.SouvsGroupsAdapter;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.RecyclerClickListener;
import com.globalpaysolutions.yocomprorecarga.utils.RecyclerTouchListener;
import com.globalpaysolutions.yocomprorecarga.views.SouvenirsGroupsView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SouvenirsGroups extends AppCompatActivity implements SouvenirsGroupsView
{
    private static final String TAG = SouvenirsGroups.class.getSimpleName();

    private SouvenirsGroupPresenterImpl mPresenter;

    ImageView ivBackground;
    ImageView btnBack;
    ImageView btnClaim;
    ImageView icLeftTower;
    ImageView icRightTower;
    ImageView icStar0;
    ImageView icStar1;
    ImageView icStar2;
    ImageView btnLeaderboards;
    ImageView btnStore;
    TextView tvProgress;
    RecyclerView gvGroups;
    ProgressDialog mProgressDialog;

    SouvsGroupsAdapter mGroupsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_souvenirs_groups);

        ivBackground = (ImageView) findViewById(R.id.ivBackground);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnClaim = (ImageView) findViewById(R.id.btnClaim);
        icLeftTower = (ImageView) findViewById(R.id.icLeftTower);
        icRightTower = (ImageView) findViewById(R.id.icRightTower);
        tvProgress = (TextView) findViewById(R.id.tvProgress);
        icStar0 = (ImageView) findViewById(R.id.icStar0);
        icStar1 = (ImageView) findViewById(R.id.icStar1);
        icStar2 = (ImageView) findViewById(R.id.icStar2);
        btnLeaderboards = (ImageView) findViewById(R.id.btnLeaderboards);
        btnStore = (ImageView) findViewById(R.id.btnStore);
        gvGroups = (RecyclerView) findViewById(R.id.gvGroups);

        mPresenter = new SouvenirsGroupPresenterImpl(this, this, this);

        mPresenter.init();
        mPresenter.createGroupsArray();
        mPresenter.retrieveProgress();
        mPresenter.retrieveGroupedSouvenirs();

    }

    @Override
    public void initializeViews(String progress, int stars)
    {
        try
        {
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.color_gray_3));

            tvProgress.setText(String.format(getString(R.string.label_souvs_progress), progress));
            updateStars(stars);

            //Loads images
            Picasso.with(this).load(R.drawable.bg_background_4).into(ivBackground);
            Picasso.with(this).load(R.drawable.ic_worldcup_theme_left_tower).into(icLeftTower);
            Picasso.with(this).load(R.drawable.ic_worldcup_theme_right_tower).into(icRightTower);

            //Sets click listener
            btnBack.setOnClickListener(backListener);
            btnLeaderboards.setOnClickListener(leaderboardsListener);
            btnStore.setOnClickListener(storeListener);
            btnClaim.setOnClickListener(claimComboListener);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error initializing views: " + ex.getMessage());
        }
    }

    @Override
    public void renderGroups(final List<GroupSouvenirModel> groups)
    {
        try
        {
            mGroupsAdapter = new SouvsGroupsAdapter(this, groups);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);

            gvGroups.setLayoutManager(layoutManager);
            gvGroups.setItemAnimator(new DefaultItemAnimator());
            gvGroups.setAdapter(mGroupsAdapter);

            gvGroups.addOnItemTouchListener(new RecyclerTouchListener(this, gvGroups, new RecyclerClickListener()
            {
                @Override
                public void onClick(View view, int position)
                {
                    GroupSouvenirModel group = groups.get(position);
                    ButtonAnimator.getInstance(SouvenirsGroups.this).animateButton(view);
                    Intent souvenirs = new Intent(SouvenirsGroups.this, SouvenirsGrouped.class);
                    souvenirs.putExtra(Constants.BUNDLE_SOUVENIRS_GROUP_SELCTED, group.getGroup());
                    souvenirs.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(souvenirs);
                    finish();
                }

                @Override
                public void onLongClick(View view, int position)
                {
                    Log.i(TAG, "Item long clicked");
                }
            }));

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error rendering groups: " + ex.getMessage());
        }
    }

    @Override
    public void updateSouvsProgress(String progress, int stars)
    {
        try
        {
            tvProgress.setText(String.format(getString(R.string.label_souvs_progress), progress));
            updateStars(stars);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error updating progress: " + ex.getMessage());
        }
    }

    @Override
    public void showLoadingDialog(String text)
    {
        try
        {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(text);
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
    public void showGenericDialog(DialogViewModel content, View.OnClickListener clickListener)
    {
        try
        {
            final AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog_generic, null);

            TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvDialogTitle);
            TextView tvDescription = (TextView) dialogView.findViewById(R.id.tvDialogMessage);
            ImageView button = (ImageView) dialogView.findViewById(R.id.btnClose);

            tvTitle.setText(content.getTitle());
            tvDescription.setText(content.getLine1());

            dialog = builder.setView(dialogView).create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            if(clickListener != null)
            {
                button.setOnClickListener(clickListener);
            }
            else
            {
                button.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();
                    }
                });
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
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

    private View.OnClickListener storeListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            ButtonAnimator.getInstance(SouvenirsGroups.this).animateButton(view);
            Intent store = new Intent(SouvenirsGroups.this, Store.class);
            store.putExtra(Constants.BUNDLE_STORE_BACK_STACK, Constants.StoreNavigationStack.SOUVENIRS_GROUPS);
            store.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(store);
            finish();
        }
    };

    private View.OnClickListener leaderboardsListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            ButtonAnimator.getInstance(SouvenirsGroups.this).animateButton(view);
            Intent leaderboards = new Intent(SouvenirsGroups.this, Leaderboards.class);
            leaderboards.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(leaderboards);
            finish();
        }
    };

    private View.OnClickListener backListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            ButtonAnimator.getInstance(SouvenirsGroups.this).animateButton(view);
            navigateBack();
        }
    };

    private View.OnClickListener claimComboListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            ButtonAnimator.getInstance(SouvenirsGroups.this).animateButton(view);
            Intent combos = new Intent(SouvenirsGroups.this, Combos.class);
            combos.putExtra(Constants.BUNDLE_COMBOS_BACK_STACK, Constants.CombosNavigationStack.SOUVENIRs_GROUPED);
            combos.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(combos);
            finish();
        }
    };

    private void navigateBack()
    {
        Intent backAction = new Intent(this, Profile.class);
        backAction.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(backAction);
        finish();
    }

    private void updateStars(int stars)
    {
        switch (stars)
        {
            case 1:
                Picasso.with(this).load(R.drawable.ic_star_on).into(icStar0);
                break;
            case 2:
                Picasso.with(this).load(R.drawable.ic_star_on).into(icStar0);
                Picasso.with(this).load(R.drawable.ic_star_on).into(icStar1);
                break;
            case 3:
                Picasso.with(this).load(R.drawable.ic_star_on).into(icStar0);
                Picasso.with(this).load(R.drawable.ic_star_on).into(icStar1);
                Picasso.with(this).load(R.drawable.ic_star_on).into(icStar2);
                break;
        }
    }
}
