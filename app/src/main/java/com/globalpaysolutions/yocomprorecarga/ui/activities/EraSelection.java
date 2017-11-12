package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.api.AgesListModel;
import com.globalpaysolutions.yocomprorecarga.presenters.EraSelectionPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.ErasAdapter;
import com.globalpaysolutions.yocomprorecarga.views.EraSelectionView;

import java.util.List;

public class EraSelection extends AppCompatActivity implements EraSelectionView
{
    private static final String TAG = EraSelection.class.getSimpleName();

    //MVP
    EraSelectionPresenterImpl mPresenter;

    //Layouts and Views
    ListView lvEras;

    //Global Variables
    ErasAdapter mErasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_era_selection);

        lvEras = (ListView) findViewById(R.id.lvEras);

        mErasAdapter = new ErasAdapter(this, R.layout.custom_era_selection_item);
        mPresenter = new EraSelectionPresenterImpl(this, this, this);

        lvEras.setAdapter(mErasAdapter);

        mPresenter.retrieveEras();
    }

    @Override
    public void initialViews()
    {

    }

    @Override
    public void renderEras(List<AgesListModel> eras)
    {
        mErasAdapter.notifyDataSetChanged();
        lvEras.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                AgesListModel currentItem = ((AgesListModel) parent.getItemAtPosition(position));
                //TODO: Quitar toast
                Toast.makeText(EraSelection.this, String.format("Clickeaste una era: %1$s", currentItem.getName()), Toast.LENGTH_LONG).show();
            }
        });

        //Llenado de items en el ListView
        try
        {
            mErasAdapter.clear();
            for (AgesListModel item : eras)
            {
                mErasAdapter.add(item);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
