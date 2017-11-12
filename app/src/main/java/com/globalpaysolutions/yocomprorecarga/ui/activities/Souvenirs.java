package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.api.ListSouvenirsByConsumer;
import com.globalpaysolutions.yocomprorecarga.presenters.SourvenirsPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.SouvenirsAdapter;
import com.globalpaysolutions.yocomprorecarga.views.SouvenirsView;

import java.util.List;

public class Souvenirs extends AppCompatActivity implements SouvenirsView
{
    private static final String TAG = Souvenirs.class.getSimpleName();

    //MVP
    SourvenirsPresenterImpl mPresnter;

    //Views and Layouts
    SouvenirsAdapter mSouvenirsAdapter;
    GridView gvSouvenirs;

    //Global Variables

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_souvenirs);

        //Initialize Views
        gvSouvenirs = (GridView) findViewById(R.id.gvSouvenirs);

        //Initialize objects
        mPresnter = new SourvenirsPresenterImpl(this, this, this);
        mSouvenirsAdapter = new SouvenirsAdapter(this, R.layout.custom_souvenir_gridview_item);

        gvSouvenirs.setAdapter(mSouvenirsAdapter);

        mPresnter.requestSouvenirs();
    }

    @Override
    public void renderSouvenirs(List<ListSouvenirsByConsumer> souvenirs)
    {
        try
        {
            mSouvenirsAdapter.notifyDataSetChanged();
            mSouvenirsAdapter.clear();
            for (ListSouvenirsByConsumer item : souvenirs)
            {
                mSouvenirsAdapter.add(item);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }
}
