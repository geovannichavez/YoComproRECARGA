package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.api.Combo;
import com.globalpaysolutions.yocomprorecarga.presenters.CombosPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.CombosAdapter;
import com.globalpaysolutions.yocomprorecarga.views.CombosView;

import java.util.ArrayList;
import java.util.List;

public class Combos extends AppCompatActivity implements CombosView
{
    private static final String TAG = Combos.class.getSimpleName();
    private CombosPresenterImpl mPresenter;


    private RecyclerView mRecyclerView;
    private CombosAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combos);

        mRecyclerView = (RecyclerView) findViewById(R.id.lvCombos);

        mPresenter = new CombosPresenterImpl(this, this, this);
        mPresenter.initialize();
        mPresenter.retrieveCombos();

    }

    @Override
    public void initialViews()
    {

    }

    @Override
    public void renderCombos(List<Combo> combos)
    {
        try
        {
            mAdapter = new CombosAdapter(this, combos);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(mAdapter);

            mAdapter.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
