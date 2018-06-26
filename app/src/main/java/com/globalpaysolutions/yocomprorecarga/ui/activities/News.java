package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.api.New;
import com.globalpaysolutions.yocomprorecarga.presenters.NewsPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.NewsAdapter;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.NavFlagsUtil;
import com.globalpaysolutions.yocomprorecarga.views.NewsView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class News extends AppCompatActivity implements NewsView
{
    private static final String TAG = News.class.getSimpleName();

    NewsPresenterImpl mPresenter;
    NewsAdapter mAdapter;

    //Views
    private RecyclerView lvNews;
    private ImageView bgTimemachine;
    private ImageView btnBack;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        lvNews = (RecyclerView) findViewById(R.id.lvNews);
        bgTimemachine = (ImageView) findViewById(R.id.bgTimemachine);
        btnBack = (ImageView) findViewById(R.id.btnBack);

        mPresenter = new NewsPresenterImpl(this, this, this);
        mPresenter.initialize();
    }

    @Override
    public void initialize()
    {
        Picasso.with(this).load(R.drawable.bg_background_4).into(bgTimemachine);
        mPresenter.retrieveNews();
        btnBack.setOnClickListener(backListener);
    }

    @Override
    public void renderNews(List<New> response)
    {
        try
        {
            mAdapter = new NewsAdapter(News.this, response);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplication());

            lvNews.setLayoutManager(layoutManager);
            lvNews.setItemAnimator(new DefaultItemAnimator());
            lvNews.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            lvNews.setAdapter(mAdapter);

            mAdapter.notifyDataSetChanged();
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void showLoadingDialog(String label)
    {
        try
        {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(label);
            mProgressDialog.show();
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
    public void showGenericDialog(DialogViewModel content)
    {
        try
        {
            final AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog_generic, null);

            TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvDialogTitle);
            TextView tvDescription = (TextView) dialogView.findViewById(R.id.tvDialogMessage);
            ImageView button = (ImageView) dialogView.findViewById(R.id.btnClose);

            tvTitle.setText(content.getTitle());
            tvDescription.setText(content.getLine1());

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

    private View.OnClickListener backListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            ButtonAnimator.getInstance(News.this).animateButton(view);
            navigateBack();
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            navigateBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void navigateBack()
    {
        Intent back = new Intent(this, Main.class);
        NavFlagsUtil.addFlags(back);
        startActivity(back);
        finish();
    }
}
