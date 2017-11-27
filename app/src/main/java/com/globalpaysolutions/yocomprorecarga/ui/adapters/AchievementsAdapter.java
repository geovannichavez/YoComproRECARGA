package com.globalpaysolutions.yocomprorecarga.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.globalpaysolutions.yocomprorecarga.models.api.ListAchievementsByConsumer;

/**
 * Created by Josué Chávez on 16/11/2017.
 */

public class AchievementsAdapter extends ArrayAdapter<ListAchievementsByConsumer>
{
    private Context mContext;
    private int mResource;
    private CallbackManager callbackManager = CallbackManager.Factory.create();

    public AchievementsAdapter(@NonNull Context context, int resource)
    {
        super(context, resource);
        this.mContext = context;
        this.mResource = resource;
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View row;

        if (convertView == null)
            row = inflater.inflate(mResource, parent, false);

        else
            row = convertView;


        final ListAchievementsByConsumer currentItem = getItem(position);

        if(row != null)
        {
            row = inflater.inflate(mResource, parent, false);
            row.setTag(currentItem);
        }

        TextView title = (TextView) row.findViewById(R.id.txtTitle);
        TextView description = (TextView) row.findViewById(R.id.txtDescription);
        TextView coinsValue = (TextView) row.findViewById(R.id.txtCoins);
        TextView actual = (TextView) row.findViewById(R.id.txtActual);
        ImageView level = (ImageView) row.findViewById(R.id.imgAchvCounter);
        ImageView imgCoins = (ImageView) row.findViewById(R.id.imgCoins);
        ImageButton btnFacebook = (ImageButton) row.findViewById(R.id.btnFacebook);
        final ShareButton btnShare = (ShareButton) row.findViewById(R.id.btnShare);
        ShareDialog shareDialog;

        String currentLevel = String.valueOf(currentItem.getLevel());
        String score = String.valueOf(currentItem.getScore());
        String next = String.valueOf(currentItem.getNextLevel());

        btnFacebook.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnShare.performClick();
            }
        });

        if (ShareDialog.canShow(ShareLinkContent.class))
        {
            ShareLinkContent shareContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("http://recar-go.com/share/ShareAchievement"))
                    .setQuote(String.format("Acabo de lograr el nivel %1$s de %2$s en RecarGO!", currentLevel, currentItem.getName()))
                    /*.setShareHashtag(new ShareHashtag.Builder().setHashtag("LoLogre").build())*/
                    .build();
            btnShare.setShareContent(shareContent);
        }

        //Share
        shareDialog = new ShareDialog((Activity) mContext);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>()
        {
            @Override
            public void onSuccess(Sharer.Result result)
            {

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

        title.setText(currentItem.getName());
        description.setText(currentItem.getDescription());
        coinsValue.setText(String.valueOf(currentItem.getNextPrize()));

        String strActual = String.format("Progreso: %1$s/%2$s", score, next);
        actual.setText(strActual);

        switch (currentItem.getLevel())
        {
            case 0:
                level.setImageResource(R.drawable.ic_achvs_counter_0);
                btnFacebook.setEnabled(false);
                btnShare.setEnabled(false);
                btnFacebook.setVisibility(View.INVISIBLE);
                break;
            case 1:
                level.setImageResource(R.drawable.ic_achvs_counter_1);
                break;
            case 2:
                level.setImageResource(R.drawable.ic_achvs_counter_2);
                break;
            case 3:
                level.setImageResource(R.drawable.ic_achvs_counter_3);
                actual.setVisibility(View.INVISIBLE);
                imgCoins.setVisibility(View.INVISIBLE);
                coinsValue.setVisibility(View.INVISIBLE);
                break;
            default:
                level.setImageResource(R.drawable.ic_achvs_counter_0);
                break;
        }

        return  row;
    }
}
