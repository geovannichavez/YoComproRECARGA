package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.ImmersiveActivity;
import com.globalpaysolutions.yocomprorecarga.views.SouvenirsView;
import com.squareup.picasso.Picasso;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Souvenirs extends ImmersiveActivity implements SouvenirsView
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
    ImageView bgTimemachine;
    ProgressDialog mProgressDialog;

    //Global Variables

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

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
        bgTimemachine = (ImageView) findViewById(R.id.bgTimemachine);

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.getInstance(Souvenirs.this).animateButton(v);
                Intent main = new Intent(Souvenirs.this, Profile.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(main);
                finish();
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
                finish();
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
    public void showLoadingDialog(String label)
    {
        try
        {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(label);
            mProgressDialog.show();
            //mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void hideLoadingDialog()
    {
        try
        {
            if (mProgressDialog != null && mProgressDialog.isShowing())
            {
                mProgressDialog.dismiss();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void setInitialViewsState(String eraName)
    {
        try
        {
            Picasso.with(this).load(R.drawable.bg_time_machine).into(bgTimemachine);

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
                if(souvenir.getSouvenirsOwnedByConsumer() > 0)
                {
                    mPresnter.showSouvenirDetailsModal(souvenir.getTitle(), souvenir.getDescription(),
                            String.valueOf(souvenir.getSouvenirsOwnedByConsumer()),
                            souvenir.getImgUrl(),
                            souvenir.getSouvenirID());
                }
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
                    ButtonAnimator.getInstance(Souvenirs.this).animateButton(v);
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
    public void showNewAchievementDialog(String name, String level, String prize, String score, int resource)
    {
        try
        {
            final AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_achievement_dialog, null);

            TextView lblReward = (TextView) dialogView.findViewById(R.id.lblReward);
            TextView lblAchievementName = (TextView) dialogView.findViewById(R.id.lblAchievementName);
            ImageView imgAchievement = (ImageView) dialogView.findViewById(R.id.imgAchievement);
            ImageButton btnClose = (ImageButton) dialogView.findViewById(R.id.btnClose);
            ImageButton btnAchievemtsNav = (ImageButton) dialogView.findViewById(R.id.btnAchievemtsNav);

            lblReward.setText(String.format("Tu recompensa es de %1$s RecarCoins",prize));
            lblAchievementName.setText(String.format("Has logrado el nivel %1$s  de %2$s",level, name ));
            Picasso.with(this).load(resource).into(imgAchievement);

            dialog = builder.setView(dialogView).create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            btnClose.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ButtonAnimator.getInstance(Souvenirs.this).animateButton(v);
                    Intent intent = new Intent(Souvenirs.this, PrizeDetail.class);
                    startActivity(intent);
                    finish();
                }
            });
            btnAchievemtsNav.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ButtonAnimator.getInstance(Souvenirs.this).animateButton(v);
                    Intent store = new Intent(Souvenirs.this, Achievements.class);
                    store.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(store);
                    finish();
                }
            });
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

    @Override
    public void showGenericDialog(String title, String content)
    {
        try
        {
            final AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(Souvenirs.this);
            LayoutInflater inflater = Souvenirs.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog_generic, null);

            TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvDialogTitle);
            TextView tvDescription = (TextView) dialogView.findViewById(R.id.tvDialogMessage);
            ImageView button = (ImageView) dialogView.findViewById(R.id.btnClose);

            tvTitle.setText(title);
            tvDescription.setText(content);

            dialog = builder.setView(dialogView).create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            button.setOnClickListener(new View.OnClickListener()
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

    public void closeSouvenirDialog(View view)
    {
        try
        {
            ButtonAnimator.getInstance(Souvenirs.this).animateButton(view);
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
            Picasso.with(this).load(resource).into(imgSouvenir);

            dialog = builder.setView(dialogView).create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            btnClose.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ButtonAnimator.getInstance(Souvenirs.this).animateButton(v);
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
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
