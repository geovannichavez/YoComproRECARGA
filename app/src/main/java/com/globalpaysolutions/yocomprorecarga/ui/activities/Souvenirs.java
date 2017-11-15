package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.api.ListSouvenirsByConsumer;
import com.globalpaysolutions.yocomprorecarga.presenters.SourvenirsPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.SouvenirsAdapter;
import com.globalpaysolutions.yocomprorecarga.views.SouvenirsView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Souvenirs extends AppCompatActivity implements SouvenirsView
{
    private static final String TAG = Souvenirs.class.getSimpleName();

    //MVP
    SourvenirsPresenterImpl mPresnter;

    //Views and Layouts
    SouvenirsAdapter mSouvenirsAdapter;
    GridView gvSouvenirs;
    AlertDialog mSouvenirDialog;

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
        gvSouvenirs.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                ListSouvenirsByConsumer souvenir = ((ListSouvenirsByConsumer) parent.getItemAtPosition(position));
                mPresnter.showSouvenirDetailsModal(souvenir.getTitle(), "", String.valueOf(souvenir.getSouvenirsOwnedByConsumer()), souvenir.getImgUrl());
            }
        });


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

    @Override
    public void showSouvenirDetails(String title, String description, String count, String url)
    {
        try
        {
            //Creates the builder and inflater of dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(Souvenirs.this);
            LayoutInflater inflater = Souvenirs.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog_souvenir_detail, null);

            TextView lblSouvenirNameDialog = (TextView) dialogView.findViewById(R.id.lblSouvenirNameDialog);
            TextView lblSouvenirDialogDescr = (TextView) dialogView.findViewById(R.id.lblSouvenirDialogDescr);
            TextView lblSouvenirDialogQntt = (TextView) dialogView.findViewById(R.id.lblSouvenirDialogQntt);

            ImageView imgSouvenirDetail = (ImageView) dialogView.findViewById(R.id.imgSouvenirDetail);

            lblSouvenirDialogQntt.setText(count);
            lblSouvenirNameDialog.setText(title);
            lblSouvenirDialogDescr.setText(description);

            //TODO: Architecture violation - Requests made on Views
            Picasso.with(this).load(url).into(imgSouvenirDetail);

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

    public void closeSouvenirDialog(View view)
    {
        try
        {
            if(mSouvenirDialog != null)
                mSouvenirDialog.dismiss();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
