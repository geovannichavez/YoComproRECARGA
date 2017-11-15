package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.api.ListGameStoreResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ListSouvenirsByConsumer;
import com.globalpaysolutions.yocomprorecarga.presenters.StorePresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.StoreAdapter;
import com.globalpaysolutions.yocomprorecarga.views.StoreView;

import java.util.ArrayList;
import java.util.Collections;
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

    //Global Variables
    List<ListGameStoreResponse> mStoreItems;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        imgStoreItem = (ImageView) findViewById(R.id.imgStoreItem);
        pagerStoreItems = (ViewPager) findViewById(R.id.pagerStoreItems);
        btnLeft = (ImageButton) findViewById(R.id.btnLeft);
        btnRight = (ImageButton) findViewById(R.id.btnRight);

        mPresenter = new StorePresenterImpl(this, this, this);
        mPresenter.retrieveStoreItems();
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

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener()
    {

        @Override
        public void onPageSelected(int position)
        {
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

}
