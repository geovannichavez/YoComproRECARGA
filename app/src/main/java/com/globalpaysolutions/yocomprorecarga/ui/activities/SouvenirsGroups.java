package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.GroupSouvenirModel;
import com.globalpaysolutions.yocomprorecarga.presenters.SouvenirsGroupPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.SouvsGroupsAdapter;
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
    ImageView icLeftTower;
    ImageView icRightTower;
    RecyclerView gvGroups;

    SouvsGroupsAdapter mGroupsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_souvenirs_groups);

        ivBackground = (ImageView) findViewById(R.id.ivBackground);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        icLeftTower = (ImageView) findViewById(R.id.icLeftTower);
        icRightTower = (ImageView) findViewById(R.id.icRightTower);
        gvGroups = (RecyclerView) findViewById(R.id.gvGroups);

        mPresenter = new SouvenirsGroupPresenterImpl(this, this, this);

        mPresenter.init();
        mPresenter.createGroupsArray();

    }

    @Override
    public void initializeViews()
    {
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.color_gray_3));

        //Loads images
        Picasso.with(this).load(R.drawable.bg_background_4).into(ivBackground);
        Picasso.with(this).load(R.drawable.ic_worldcup_theme_left_tower).into(icLeftTower);
        Picasso.with(this).load(R.drawable.ic_worldcup_theme_right_tower).into(icRightTower);

    }

    @Override
    public void renderGroups(List<GroupSouvenirModel> groups)
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
                    Toast.makeText(SouvenirsGroups.this, "Clicked!", Toast.LENGTH_LONG).show();
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
}
