package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.RewardItem;
import com.globalpaysolutions.yocomprorecarga.presenters.LikesPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.LikesAdapter;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.LikesClickListener;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.LikesView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Likes extends AppCompatActivity implements LikesView
{
    private static final String TAG = Likes.class.getSimpleName();

    //Images and layouts
    ImageView btnBack;
    ImageView bgTimemachine;
    ProgressDialog mProgressDialog;

    //MVP
    LikesPresenterImpl mPresenter;

    //Global Variables
    private RecyclerView lvLikes;
    private CallbackManager mCallbackManager = CallbackManager.Factory.create();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);

        lvLikes = (RecyclerView) findViewById(R.id.lvLikes);
        btnBack = (ImageView)findViewById(R.id.btnBack);
        bgTimemachine = (ImageView) findViewById(R.id.bgTimemachine);

        mPresenter = new LikesPresenterImpl(this, this, this);

        //Share
        ShareDialog mShareDialog = new ShareDialog(this);
        mShareDialog.registerCallback(mCallbackManager, new FacebookCallback<Sharer.Result>()
        {
            @Override
            public void onSuccess(Sharer.Result result)
            {
                mPresenter.requestReward();
            }

            @Override
            public void onCancel()
            {

            }

            @Override
            public void onError(FacebookException error)
            {

            }
        });

        mPresenter.initialize();
        mPresenter.createItems();

    }

    @Override
    public void initialViews()
    {
        Picasso.with(this).load(R.drawable.bg_time_machine).into(bgTimemachine);
    }

    @Override
    public void renderRewards(List<RewardItem> rewards)
    {
        try
        {
            LikesAdapter rewardsAdapter = new LikesAdapter(this, rewards, mPresenter, new LikesClickListener()
            {
                @Override
                public void onClickListener(int position, ShareButton shareButton, Constants.FacebookActions actions)
                {

                    if (ShareDialog.canShow(ShareLinkContent.class))
                    {
                        ShareLinkContent shareContent =  null;
                        switch (actions)
                        {
                            case SHARE_PROFILE:
                                String nickname = UserData.getInstance(Likes.this).getNickname();
                                String phrase = String.format(Likes.this.getString(R.string.facebook_share_content_im_a_player), nickname);

                                shareContent = new ShareLinkContent.Builder()
                                        .setContentUrl(Uri.parse(Constants.FACEBOOK_PLAYER_URL))
                                        .setQuote(phrase)
                                        .setShareHashtag(new ShareHashtag.Builder().setHashtag("RecarGO").build())
                                        .build();
                                break;
                            case SHARE_PAGE:
                                shareContent = new ShareLinkContent.Builder()
                                        .setContentUrl(Uri.parse(Constants.FACEBOOK_FANPAGE_URL))
                                        .setShareHashtag(new ShareHashtag.Builder().setHashtag("SiempreGanas").build())
                                        .build();
                                break;
                        }

                        shareButton.setShareContent(shareContent);

                    }

                    shareButton.performClick();
                }
            });
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplication());
            lvLikes.setLayoutManager(layoutManager);
            lvLikes.setItemAnimator(new DefaultItemAnimator());
            lvLikes.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            lvLikes.setAdapter(rewardsAdapter);

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error rendering rewards: " + ex.getMessage());
        }
    }


    @Override
    public void setClickListeners()
    {
        btnBack.setOnClickListener(backListener);
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
    public void showGenericImageDialog(DialogViewModel content, int ic_alert, View.OnClickListener clickListener)
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

            tvTitle.setText(content.getTitle());
            tvDescription.setText(content.getLine1());
            Picasso.with(this).load(ic_alert).into(imgSouvenir);

            dialog = builder.setView(dialogView).create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            if(clickListener == null)
            {
                btnClose.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        ButtonAnimator.getInstance(Likes.this).animateButton(v);
                        dialog.dismiss();
                    }
                });
            }
            else
            {
                btnClose.setOnClickListener(clickListener);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /*
    *
    *   CLICK LISTENERS
    *
    * */


    private void backNavigation()
    {
        Intent back = new Intent(Likes.this, Store.class);
        back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(back);
        finish();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            backNavigation();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private View.OnClickListener backListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
           backNavigation();
        }
    };
}
