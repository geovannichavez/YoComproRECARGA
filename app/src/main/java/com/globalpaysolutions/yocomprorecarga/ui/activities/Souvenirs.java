package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
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
    TextView tvEraName;
    ImageButton btnBack;
    ImageButton btnStore;

    //Global Variables

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_souvenirs);

        //Initialize Views
        gvSouvenirs = (GridView) findViewById(R.id.gvSouvenirs);
        tvEraName = (TextView) findViewById(R.id.tvEraName);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnStore = (ImageButton) findViewById(R.id.btnStore);

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent main = new Intent(Souvenirs.this, Profile.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(main);
            }
        });

        btnStore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent main = new Intent(Souvenirs.this, Store.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(main);
            }
        });

        //Initialize objects
        mPresnter = new SourvenirsPresenterImpl(this, this, this);
        mSouvenirsAdapter = new SouvenirsAdapter(this, R.layout.custom_souvenir_gridview_item);

        gvSouvenirs.setAdapter(mSouvenirsAdapter);

        mPresnter.initializeViews();
        mPresnter.requestSouvenirs();
    }

    @Override
    public void setInitialViewsState(String eraName)
    {
        try
        {
            tvEraName.setText(eraName);
        }
        catch (Exception ex) { ex.printStackTrace();}
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
                mPresnter.showSouvenirDetailsModal(souvenir.getTitle(), "",
                        String.valueOf(souvenir.getSouvenirsOwnedByConsumer()),
                        souvenir.getImgUrl(),
                        souvenir.getSouvenirID());
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
    public void showSouvenirDetails(String title, String description, String count, String url, final int souvID)
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
            ImageView btnSouvDialgSell = (ImageView) dialogView.findViewById(R.id.btnSouvDialgSell);
            btnSouvDialgSell.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mPresnter.exchangeSouvenir(souvID);
                }
            });

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

    @Override
    public void navigatePrizeDetails()
    {
        Intent intent = new Intent(this, PrizeDetail.class);
        startActivity(intent);
    }

    @Override
    public void closeSouvenirDialog()
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

    @Override
    public void generateImageDialog(DialogViewModel dialog, int resource)
    {
        createImageDialog(dialog.getTitle(), dialog.getLine1(), resource);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent main = new Intent(this, Profile.class);
            main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(main);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
