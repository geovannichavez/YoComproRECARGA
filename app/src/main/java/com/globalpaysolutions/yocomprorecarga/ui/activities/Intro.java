package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.IntroPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.TutorialAdapter;
import com.globalpaysolutions.yocomprorecarga.views.IntroView;

import java.util.ArrayList;
import java.util.Collections;

import me.relex.circleindicator.CircleIndicator;

public class Intro extends AppCompatActivity implements IntroView
{
    private static final String TAG = Intro.class.getSimpleName();

    //MVP
    IntroPresenterImpl mPresenter;

    //Global variables
    Integer[] mSlides;

    //Layouts and Views
   /* Button btnSkip;
    Button btnNext;*/
    ViewPager tutorialPager;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mPresenter = new IntroPresenterImpl(this, this, this);
        mPresenter.initializeView();
        mPresenter.setIntroAsRead();

       /* btnSkip = (Button) findViewById(R.id.btnSkip);
        btnNext = (Button) findViewById(R.id.btnNext);*/

    }

    @Override
    public void initialViewsState()
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //setContentView(R.layout.activity_intro);
    }

    @Override
    public void navigateTermsConditions()
    {
        Intent termsConditions = new Intent(Intro.this, AcceptTerms.class);
        startActivity(termsConditions);
    }

    @Override
    public void showTutorial()
    {
        /*int currentPage = 0;
        mSlides = new Integer[]{    R.drawable.img_tuto_0,
                                R.drawable.img_tuto_1,
                                R.drawable.img_tuto_2,
                                R.drawable.img_tuto_3,
                                R.drawable.img_tuto_4   };
        ArrayList<Integer> tutorialArray = new ArrayList<>();

        try
        {
            //Finds all views once the parent view is inflated
            tutorialPager = (ViewPager) findViewById(R.id.pager);
            //CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);

            Collections.addAll(tutorialArray, mSlides);

            tutorialPager.setAdapter(new TutorialAdapter(Intro.this, tutorialArray));
            tutorialPager.addOnPageChangeListener(viewPagerPageChangeListener);
            indicator.setViewPager(tutorialPager);

            if (currentPage == mSlides.length)
            {
                currentPage = 0;
            }
            tutorialPager.setCurrentItem(currentPage++, true);
        }
        catch (Exception ex) {   ex.printStackTrace();   }*/
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener()
    {

        @Override
        public void onPageSelected(int position)
        {
            /*// changing the next button text 'NEXT' / 'GOT IT'
            if (position == mSlides.length - 1)
            {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.button_start));
                btnSkip.setVisibility(View.GONE);
            }
            else
            {
                // still pages are left
                btnNext.setText(getString(R.string.button_next));
                btnSkip.setVisibility(View.VISIBLE);
            }*/
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

    public void skipClick(View view)
    {
        mPresenter.navigateNext();
    }

    public void nextClick(View view)
    {
        try
        {
            int current = tutorialPager.getCurrentItem() + 1;
            if (current < mSlides.length)
                tutorialPager.setCurrentItem(current);
            else
                mPresenter.navigateNext();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
