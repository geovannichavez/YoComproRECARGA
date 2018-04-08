package com.globalpaysolutions.yocomprorecarga.ui.activities;

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
import com.globalpaysolutions.yocomprorecarga.models.api.ListSouvenirsByConsumer;
import com.globalpaysolutions.yocomprorecarga.presenters.SouvenirsGroupedPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.SouvsGroupedAdapter;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.FastClickUtil;
import com.globalpaysolutions.yocomprorecarga.utils.RecyclerClickListener;
import com.globalpaysolutions.yocomprorecarga.utils.RecyclerTouchListener;
import com.globalpaysolutions.yocomprorecarga.views.SouvenirsGroupedView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SouvenirsGrouped extends AppCompatActivity implements SouvenirsGroupedView
{
    private static final String TAG = SouvenirsGrouped.class.getSimpleName();

    AlertDialog mSouvenirDialog;
    ImageView ivBackground;
    ImageView btnBack;
    ImageView icTutorial;
    ImageView icArrowLeft;
    ImageView icArrowRight;
    ImageView btnStore;
    ImageView btnLeaderboards;
    TextView tvGroupName;
    RecyclerView gvSouvs;

    String mGroup;
    SouvsGroupedAdapter mSouvsAdapter;
    SouvenirsGroupedPresenterImpl mPresenter;

    List<ListSouvenirsByConsumer> mCurrentDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_souvenirs_grouped);

        mGroup = getIntent().getStringExtra(Constants.BUNDLE_SOUVENIRS_GROUP_SELCTED);

        ivBackground = (ImageView) findViewById(R.id.ivBackground);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        icTutorial = (ImageView) findViewById(R.id.icTutorial);
        icArrowLeft = (ImageView) findViewById(R.id.icArrowLeft);
        icArrowRight = (ImageView) findViewById(R.id.icArrowRight);
        btnStore = (ImageView) findViewById(R.id.btnStore);
        btnLeaderboards = (ImageView) findViewById(R.id.btnLeaderboards);
        tvGroupName = (TextView) findViewById(R.id.tvGroupName);
        gvSouvs = (RecyclerView) findViewById(R.id.gvSouvs);

        mPresenter = new SouvenirsGroupedPresenterImpl(this, this, this);
        mPresenter.init(mGroup);
        mPresenter.processGroup(mGroup);
    }

    @Override
    public void init(String groupName)
    {
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.color_gray_3));

        //Sets click listeners
        btnBack.setOnClickListener(backClickListener);
        //icTutorial.setOnClickListener(tutorialListener);
        icArrowLeft.setOnClickListener(leftListener);
        icArrowRight.setOnClickListener(rightListener);
        btnStore.setOnClickListener(storeListener);
        btnLeaderboards.setOnClickListener(leadeboardListener);

        Picasso.with(this).load(R.drawable.bg_background_4).into(ivBackground);
        tvGroupName.setText(groupName);
    }

    @Override
    public void renderSouvs(final List<ListSouvenirsByConsumer> souvsList, String groupUpdated)
    {
        try
        {
            mGroup = groupUpdated;

            mCurrentDisplay = new ArrayList<>();
            mCurrentDisplay.addAll(souvsList);

            mSouvsAdapter = new SouvsGroupedAdapter(this, souvsList);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);

            gvSouvs.setLayoutManager(layoutManager);
            gvSouvs.setItemAnimator(new DefaultItemAnimator());
            gvSouvs.setAdapter(mSouvsAdapter);

            gvSouvs.addOnItemTouchListener(new RecyclerTouchListener(this, gvSouvs, new RecyclerClickListener()
            {
                @Override
                public void onClick(View view, int position)
                {
                    ListSouvenirsByConsumer souvenir = mCurrentDisplay.get(position);
                    if(souvenir.getSouvenirsOwnedByConsumer() > 0)
                    {
                        if(!FastClickUtil.isFastClick())
                        {
                            mPresenter.showSouvenirDetailsModal(souvenir.getTitle(), souvenir.getDescription(),
                                    String.valueOf(souvenir.getSouvenirsOwnedByConsumer()),
                                    souvenir.getImgUrl(),
                                    souvenir.getSouvenirID(), souvenir.getLevel());
                        }
                    }
                }

                @Override
                public void onLongClick(View view, int position)
                {

                }
            }));


        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error rendering grouped souvs: " +  ex.getMessage());
        }
    }

    @Override
    public void updateCurrentGroup(String letter)
    {
        tvGroupName.setText(letter);
    }

    @Override
    public void showSouvenirDetails(String title, String description, String count, String url, int souvID, int counterResource)
    {
        try
        {
            //Creates the builder and inflater of dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog_souvenir_detail, null);

            TextView lblSouvenirNameDialog = (TextView) dialogView.findViewById(R.id.lblSouvenirNameDialog);
            TextView lblSouvenirDialogDescr = (TextView) dialogView.findViewById(R.id.lblSouvenirDialogDescr);
            TextView lblSouvenirDialogQntt = (TextView) dialogView.findViewById(R.id.lblSouvenirDialogQntt);
            ImageView btnSouvDialgSell = (ImageView) dialogView.findViewById(R.id.btnSouvDialgSell);
            ImageView imgSouvCounter = (ImageView) dialogView.findViewById(R.id.imgSouvCounter);

            btnSouvDialgSell.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ButtonAnimator.getInstance(SouvenirsGrouped.this).animateButton(v);
                    Intent combos = new Intent(SouvenirsGrouped.this, Combos.class);
                    combos.putExtra(Constants.BUNDLE_COMBOS_BACK_STACK, Constants.CombosNavigationStack.SOUVENIRs_GROUPED);
                    combos.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(combos);
                    finish();
                }
            });

            ImageView imgSouvenirDetail = (ImageView) dialogView.findViewById(R.id.imgSouvenirDetail);

            lblSouvenirDialogQntt.setText(count);
            lblSouvenirNameDialog.setText(title);
            lblSouvenirDialogDescr.setText(description);

            Picasso.with(this).load(url).into(imgSouvenirDetail);
            Picasso.with(this).load(counterResource).into(imgSouvCounter);

            mSouvenirDialog = builder.setView(dialogView).create();
            mSouvenirDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mSouvenirDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mSouvenirDialog.show();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void setVisibleLeftArrow(boolean visible)
    {
        if(!visible)
            icArrowLeft.setVisibility(View.INVISIBLE);
        else
            icArrowLeft.setVisibility(View.VISIBLE);
    }

    @Override
    public void setVisibleRightArrow(boolean visible)
    {
        if(!visible)
            icArrowRight.setVisibility(View.INVISIBLE);
        else
            icArrowRight.setVisibility(View.VISIBLE);
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

    public void closeSouvenirDialog(View view)
    {
        try
        {
            ButtonAnimator.getInstance(this).animateButton(view);
            if(mSouvenirDialog != null)
                mSouvenirDialog.dismiss();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void navigateBack()
    {
        Intent backAction = new Intent(this, SouvenirsGroups.class);
        backAction.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(backAction);
        finish();
    }

    private View.OnClickListener backClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            ButtonAnimator.getInstance(SouvenirsGrouped.this).animateButton(view);
            navigateBack();
        }
    };

    private View.OnClickListener leftListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            //ButtonAnimator.getInstance(SouvenirsGrouped.this).animateButton(view);
            mPresenter.navigateBackward(mGroup);
        }
    };

    private View.OnClickListener rightListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            //ButtonAnimator.getInstance(SouvenirsGrouped.this).animateButton(view);
            mPresenter.navigateForward(mGroup);
        }
    };

    private View.OnClickListener storeListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            ButtonAnimator.getInstance(SouvenirsGrouped.this).animateButton(view);
            Intent store = new Intent(SouvenirsGrouped.this, Store.class);
            store.putExtra(Constants.BUNDLE_STORE_BACK_STACK, Constants.StoreNavigationStack.SOUVENIRS_GROUPS);
            store.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(store);
            finish();
        }
    };

    private View.OnClickListener leadeboardListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            ButtonAnimator.getInstance(SouvenirsGrouped.this).animateButton(view);
            Intent leaderboards = new Intent(SouvenirsGrouped.this, Leaderboards.class);
            leaderboards.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(leaderboards);
            finish();
        }
    };
}
