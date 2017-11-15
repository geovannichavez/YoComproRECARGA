package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.api.ListGameStoreResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.StorePresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.StoreAdapter;
import com.globalpaysolutions.yocomprorecarga.views.StoreView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Store extends AppCompatActivity implements StoreView
{
    //MVP
    private StorePresenterImpl mPresenter;

    //Layouyts and Views
    ImageView imgStoreItem;
    ViewPager pagerStoreItems;
    ImageButton btnLeft;
    ImageButton btnRight;
    TextView lblRecarCoinsLeft;
    ImageView btnBuy;
    ImageButton btnInfoStoreItem;

    //Global Variables
    List<ListGameStoreResponse> mStoreItems;
    int mCurrentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        imgStoreItem = (ImageView) findViewById(R.id.imgStoreItem);
        pagerStoreItems = (ViewPager) findViewById(R.id.pagerStoreItems);
        btnLeft = (ImageButton) findViewById(R.id.btnLeft);
        btnRight = (ImageButton) findViewById(R.id.btnRight);
        lblRecarCoinsLeft = (TextView) findViewById(R.id.lblRecarCoinsLeft);
        btnBuy = (ImageView) findViewById(R.id.btnBuy);
        btnInfoStoreItem = (ImageButton) findViewById(R.id.btnInfoStoreItem);

        mPresenter = new StorePresenterImpl(this, this, this);
        mPresenter.initialValues();
        mPresenter.retrieveStoreItems();
    }

    @Override
    public void setInitialValues(String currentCoins)
    {
        try
        {
            lblRecarCoinsLeft.setText(currentCoins);
            btnBuy.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(mStoreItems != null)
                    {
                        if(mStoreItems.size() > 0)
                        {
                            ListGameStoreResponse item = mStoreItems.get(mCurrentItem);

                            mPresenter.purchaseitem(item.getStoreID(), item.getValue());
                        }
                    }
                }
            });

            btnInfoStoreItem.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    createStoreItemInfoDialog();
                }
            });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void renderStoreItems(List<ListGameStoreResponse> items)
    {
        int currentPage = 0;
        mStoreItems = items;

        ArrayList<ListGameStoreResponse> storeItemsArray = new ArrayList<>();

        try
        {
            storeItemsArray.addAll(mStoreItems);

            pagerStoreItems.setAdapter(new StoreAdapter(Store.this, storeItemsArray));
            pagerStoreItems.addOnPageChangeListener(viewPagerPageChangeListener);

            if (currentPage == mStoreItems.size())
            {
                currentPage = 0;
            }
            pagerStoreItems.setCurrentItem(currentPage++, true);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void showSouvenirWonDialog(String souvenirName, String souvenirDescription, String url)
    {
        showSouvenirDialog(souvenirName, souvenirDescription, url, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                navigateSouvenirs();
            }
        });
    }

    @Override
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

    private void createStoreItemInfoDialog()
    {
        try
        {
            final AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog_souvenir_info, null);

            ImageButton btnClose = (ImageButton) dialogView.findViewById(R.id.btnClose);

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

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener()
    {
        @Override
        public void onPageSelected(int position)
        {
            mCurrentItem = position;

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == mStoreItems.size() - 1)
            {
                // last page. make button text to GOT IT
                //btnLeft.setText(getString(R.string.button_start));
                btnRight.setVisibility(View.GONE);
            }
            else if (position == 0)
            {
                btnLeft.setVisibility(View.GONE);
            }
            else
            {
                // still pages are left
                btnRight.setVisibility(View.VISIBLE);
                btnLeft.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2)
        {

        }

        @Override
        public void onPageScrollStateChanged(int arg0)
        {

        }
    };

    public void nextClick(View view)
    {
        try
        {
            int current = pagerStoreItems.getCurrentItem() + 1;
            if (current < mStoreItems.size())
                pagerStoreItems.setCurrentItem(current);
            else
                mPresenter.navigateNext();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void backClick(View view)
    {
        try
        {
            int current = pagerStoreItems.getCurrentItem() - 1;
            if (current < mStoreItems.size())
                pagerStoreItems.setCurrentItem(current);
            else
                mPresenter.navigateNext();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void navigateSouvenirs()
    {
        Intent souvenirs = new Intent(this, Souvenirs.class);
        startActivity(souvenirs);
    }

    private void showSouvenirDialog(String souvenirName, String souvenirDescription, String url, View.OnClickListener listener)
    {
        try
        {
            //Creates the builder and inflater of dialog
            final AlertDialog souvenirDialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog_won_sourvenir, null);

            TextView tvSouvenirName = (TextView) dialogView.findViewById(R.id.lblSouvenirName);
            TextView tvSouvenirDesc = (TextView) dialogView.findViewById(R.id.lblSouvenirDescription);
            ImageView imgSouvenir = (ImageView) dialogView.findViewById(R.id.imgSouvenirDialog);
            ImageButton button = (ImageButton) dialogView.findViewById(R.id.btnGenericDialogButton);
            ImageView btnClose = (ImageView) dialogView.findViewById(R.id.btnClose);
            button.setOnClickListener(listener);

            tvSouvenirName.setText(souvenirName);
            tvSouvenirDesc.setText(souvenirDescription);

            //TODO: Architecture violation - Requests made on Views
            Picasso.with(this).load(url).into(imgSouvenir);

            souvenirDialog = builder.setView(dialogView).create();
            souvenirDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            souvenirDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            btnClose.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    souvenirDialog.dismiss();
                }
            });

            souvenirDialog.show();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
